/*
 * Hibernate, Relational Persistence for Idiomatic Java
 *
 * License: GNU Lesser General Public License (LGPL), version 2.1 or later.
 * See the lgpl.txt file in the root directory or <http://www.gnu.org/licenses/lgpl-2.1.html>.
 */
package org.hibernate.hql.parser.antlr;

import org.hibernate.hql.parser.ConsumerContext;
import org.hibernate.hql.parser.ImplicitAliasGenerator;
import org.hibernate.hql.parser.ParsingContext;

/**
 * @author Steve Ebersole
 */
class ParsingContextTestingImpl implements ParsingContext {
	private final ConsumerContextTestingImpl modelMetadata = new ConsumerContextTestingImpl();
	private final ImplicitAliasGenerator implicitAliasGenerator = new ImplicitAliasGenerator();

	@Override
	public ConsumerContext getConsumerContext() {
		return modelMetadata;
	}

	@Override
	public ImplicitAliasGenerator getImplicitAliasGenerator() {
		return implicitAliasGenerator;
	}
}
