/*
 * Hibernate, Relational Persistence for Idiomatic Java
 *
 * License: GNU Lesser General Public License (LGPL), version 2.1 or later
 * See the lgpl.txt file in the root directory or http://www.gnu.org/licenses/lgpl-2.1.html
 */
package org.hibernate.query.proposed.spi;

import java.util.Iterator;
import java.util.List;

import org.hibernate.ScrollMode;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.query.proposed.QueryOptions;

/**
 * General contract for performing execution of a query returning results
 *
 * @author Steve Ebersole
 */
public interface SelectQueryPlan<R> {
	List<R> performList(
			SharedSessionContractImplementor persistenceContext,
			ExecutionContext executionContext,
			QueryOptions queryOptions,
			QueryParameterBindings inputParameterBindings);

	Iterator<R> performIterate(
			SharedSessionContractImplementor persistenceContext,
			ExecutionContext executionContext,
			QueryOptions queryOptions,
			QueryParameterBindings inputParameterBindings);

	ScrollableResultsImplementor performScroll(
			SharedSessionContractImplementor persistenceContext,
			ExecutionContext executionContext,
			QueryOptions queryOptions,
			QueryParameterBindings inputParameterBindings,
			ScrollMode scrollMode);
}
