/*
 * Hibernate, Relational Persistence for Idiomatic Java
 *
 * License: GNU Lesser General Public License (LGPL), version 2.1 or later.
 * See the lgpl.txt file in the root directory or <http://www.gnu.org/licenses/lgpl-2.1.html>.
 */
package org.hibernate.hql.parser.antlr;

import java.util.Collection;

import org.junit.Test;

import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.xpath.XPath;

import static org.junit.Assert.assertEquals;

/**
 * Simple tests to make sure the basics are working and to see a visual of the parse tree.
 *
 * @author Steve Ebersole
 */
public class HqlParserTest {
	@Test
	public void justTestIt() throws Exception {
		HqlParser parser = HqlParseTreeBuilder.INSTANCE.parseHql( "select a.b from Something a where a.c = '1'" );

		Collection<ParseTree> fromClauses = XPath.findAll( parser.statement(), "//fromClause", parser );
		assertEquals( 1, fromClauses.size() );
	}
}