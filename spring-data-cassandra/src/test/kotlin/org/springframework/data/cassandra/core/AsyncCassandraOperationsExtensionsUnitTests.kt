/*
 * Copyright 2018-2025 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.springframework.data.cassandra.core

import com.datastax.oss.driver.api.core.cql.SimpleStatement
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Test
import org.springframework.data.cassandra.core.query.Query
import org.springframework.data.cassandra.core.query.Update
import org.springframework.data.cassandra.domain.Person
import java.util.function.Consumer

/**
 * Unit tests for [AsyncCassandraOperationsExtensions].
 *
 * @author Mark Paluch
 */
class AsyncCassandraOperationsExtensionsUnitTests {

	val operations = mockk<AsyncCassandraOperations>(relaxed = true)

	// -------------------------------------------------------------------------
	// Methods dealing with static CQL
	// -------------------------------------------------------------------------

	@Test // DATACASS-484
	fun `select(String) with reified type parameter extension should call its Java counterpart`() {

		operations.select<Person>("SELECT * FROM person")
		verify { operations.select("SELECT * FROM person", Person::class.java) }
	}

	@Test // DATACASS-484
	fun `select(String, Consumer) with reified type parameter extension should call its Java counterpart`() {

		operations.select<Person>("SELECT * FROM person") { p -> p.toString() }
		verify { operations.select(eq("SELECT * FROM person"), any<Consumer<Person>>(), eq(Person::class.java)) }
	}

	@Test // DATACASS-484
	fun `selectOne(String) with reified type parameter extension should call its Java counterpart`() {

		operations.selectOne<Person>("SELECT * FROM person")
		verify { operations.selectOne("SELECT * FROM person", Person::class.java) }
	}

	// -------------------------------------------------------------------------
	// Methods dealing with com.datastax.oss.driver.api.core.cql.Statement
	// -------------------------------------------------------------------------

	@Test // DATACASS-484
	fun `select(Statement) with reified type parameter extension should call its Java counterpart`() {

		val statement = SimpleStatement.newInstance("SELECT * FROM person")
		operations.select<Person>(statement)
		verify { operations.select(statement, Person::class.java) }
	}

	@Test // DATACASS-484
	fun `select(Statement, Consumer) with reified type parameter extension should call its Java counterpart`() {

		val statement = SimpleStatement.newInstance("SELECT * FROM person")

		operations.select<Person>(statement) { p -> p.toString() }
		verify { operations.select(eq(statement), any<Consumer<Person>>(), eq(Person::class.java)) }
	}

	@Test // DATACASS-484
	fun `slice(Statement) with reified type parameter extension should call its Java counterpart`() {

		val statement = SimpleStatement.newInstance("SELECT * FROM person")

		operations.slice<Person>(statement)
		verify { operations.slice(statement, Person::class.java) }
	}

	@Test // DATACASS-484
	fun `selectOne(Statement) with reified type parameter extension should call its Java counterpart`() {

		val statement = SimpleStatement.newInstance("SELECT * FROM person")

		operations.selectOne<Person>(statement)
		verify { operations.selectOne(statement, Person::class.java) }
	}

	// -------------------------------------------------------------------------
	// Methods dealing with org.springframework.data.cassandra.core.query.Query
	// -------------------------------------------------------------------------

	@Test // DATACASS-484
	fun `select(Query) with reified type parameter extension should call its Java counterpart`() {

		operations.select<Person>(Query.empty())
		verify { operations.select(Query.empty(), Person::class.java) }
	}

	@Test // DATACASS-484
	fun `slice(Query) with reified type parameter extension should call its Java counterpart`() {

		operations.slice<Person>(Query.empty())
		verify { operations.slice(Query.empty(), Person::class.java) }
	}

	@Test // DATACASS-484
	fun `selectOne(Query) with reified type parameter extension should call its Java counterpart`() {

		operations.selectOne<Person>(Query.empty())
		verify { operations.selectOne(Query.empty(), Person::class.java) }
	}

	@Test // DATACASS-484
	fun `update(Query, Update) with reified type parameter extension should call its Java counterpart`() {

		operations.update<Person>(Query.empty(), Update.empty())
		verify { operations.update(Query.empty(), Update.empty(), Person::class.java) }
	}

	@Test // DATACASS-484
	fun `delete(Query,) with reified type parameter extension should call its Java counterpart`() {

		operations.delete<Person>(Query.empty())
		verify { operations.delete(Query.empty(), Person::class.java) }
	}

	// -------------------------------------------------------------------------
	// Methods dealing with entities
	// -------------------------------------------------------------------------

	@Test // DATACASS-484
	fun `count() with reified type parameter extension should call its Java counterpart`() {

		operations.count<Person>()
		verify { operations.count(Person::class.java) }
	}

	@Test // DATACASS-484
	fun `count(Query) with reified type parameter extension should call its Java counterpart`() {

		operations.count<Person>(Query.empty())
		verify { operations.count(Query.empty(), Person::class.java) }
	}

	@Test // DATACASS-484
	fun `exists(Any) with reified type parameter extension should call its Java counterpart`() {

		operations.exists<Person>("id")
		verify { operations.exists("id", Person::class.java) }
	}

	@Test // DATACASS-484
	fun `exists(Query) with reified type parameter extension should call its Java counterpart`() {

		operations.exists<Person>(Query.empty())
		verify { operations.exists(Query.empty(), Person::class.java) }
	}

	@Test // DATACASS-484
	fun `selectOneById(Any) with reified type parameter extension should call its Java counterpart`() {

		operations.selectOneById<Person>("id")
		verify { operations.selectOneById("id", Person::class.java) }
	}

	@Test // DATACASS-484
	fun `deleteById(Any) with reified type parameter extension should call its Java counterpart`() {

		operations.deleteById<Person>("id")
		verify { operations.deleteById("id", Person::class.java) }
	}

	@Test // DATACASS-484
	fun `truncate() with reified type parameter extension should call its Java counterpart`() {

		operations.truncate<Person>()
		verify { operations.truncate(Person::class.java) }
	}
}

