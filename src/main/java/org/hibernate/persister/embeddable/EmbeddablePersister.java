/*
 * Hibernate, Relational Persistence for Idiomatic Java
 *
 * License: GNU Lesser General Public License (LGPL), version 2.1 or later
 * See the lgpl.txt file in the root directory or http://www.gnu.org/licenses/lgpl-2.1.html
 */
package org.hibernate.persister.embeddable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.hibernate.persister.common.internal.DatabaseModel;
import org.hibernate.persister.common.internal.DomainMetamodelImpl;
import org.hibernate.persister.common.internal.Helper;
import org.hibernate.persister.common.spi.AbstractAttributeDescriptor;
import org.hibernate.persister.common.spi.AttributeContainer;
import org.hibernate.persister.common.spi.AttributeDescriptor;
import org.hibernate.persister.common.spi.Column;
import org.hibernate.persister.common.spi.OrmTypeExporter;
import org.hibernate.type.CompositeType;

/**
 * @author Steve Ebersole
 */
public class EmbeddablePersister implements OrmTypeExporter, AttributeContainer {
	private final String compositeName;
	private final String roleName;
	private final CompositeType ormType;
	private final List<Column> allColumns;

	private final Map<String, AbstractAttributeDescriptor> attributeMap = new HashMap<>();
	private final List<AbstractAttributeDescriptor> attributeList = new ArrayList<>();

	public EmbeddablePersister(
			String compositeName,
			String roleName,
			CompositeType ormType,
			DatabaseModel databaseModel,
			DomainMetamodelImpl domainMetamodel,
			List<Column> allColumns) {
		this.compositeName = compositeName;
		this.roleName = roleName;
		this.ormType = ormType;
		this.allColumns = allColumns;

		assert ormType.getPropertyNames().length == ormType.getSubtypes().length;

		int columnSpanStart = 0, columnSpanEnd;

		for ( int i = 0; i < ormType.getPropertyNames().length; i++ ) {
			final String propertyName = ormType.getPropertyNames()[i];
			final org.hibernate.type.Type propertyType = ormType.getSubtypes()[i];

			final int columnSpan = propertyType.getColumnSpan( domainMetamodel.getSessionFactory() );
			final List<Column> columns = new ArrayList<>();
			columnSpanEnd = columnSpanStart + columnSpan;
			for ( int j = columnSpanStart; j < columnSpanEnd; j++ ) {
				columns.add( allColumns.get( j ) );
			}

			final AbstractAttributeDescriptor attribute = Helper.INSTANCE.buildAttribute(
					databaseModel,
					domainMetamodel,
					this,
					propertyName,
					propertyType,
					columns
			);
			attributeMap.put( propertyName, attribute );
			attributeList.add( attribute );

			columnSpanStart = columnSpanEnd;
		}
	}

	public List<Column> collectColumns() {
		return allColumns;
	}

	@Override
	public List<AttributeDescriptor> getNonIdentifierAttributes() {
		return attributeList.stream().collect( Collectors.toList() );
	}

	@Override
	public AttributeDescriptor findAttribute(String name) {
		return attributeMap.get( name );
	}

	@Override
	public CompositeType getOrmType() {
		return ormType;
	}

	@Override
	public String asLoggableText() {
		return "EmdeddablePersister(" + roleName + " [" + compositeName + "])";
	}
}
