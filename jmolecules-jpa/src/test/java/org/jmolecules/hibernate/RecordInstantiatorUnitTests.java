/*
 * Copyright 2022-2024 the original author or authors.
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
package org.jmolecules.hibernate;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.hibernate.metamodel.spi.ValueAccess;
import org.junit.jupiter.api.Test;

/**
 * Unit tests for {@link RecordInstantiator}.
 *
 * @author Oliver Drotbohm
 */
class RecordInstantiatorUnitTests {

	@Test // #98, #125
	void instantiatesRecord() {

		RecordInstantiator instantiator = new RecordInstantiator(Address.class);

		Object[] values = RecordInstantiator.IS_AFFECTED_HIBERNATE_VERSION
				? new Object[] { "Longstreet", "12345", "Berlin", "Germany" }
				: new Object[] { "Berlin", "Germany", "12345", "Longstreet" };

		ValueAccess access = mock(ValueAccess.class);
		doReturn(values).when(access).getValues();

		assertThat(instantiator.instantiate(access, null))
				.isEqualTo(new Address("Longstreet", "12345", "Berlin", "Germany"));
	}

	record Address(String street, String postalCode, String city, String country) {}
}
