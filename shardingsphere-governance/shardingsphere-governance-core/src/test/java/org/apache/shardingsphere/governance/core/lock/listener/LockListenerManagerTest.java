/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.shardingsphere.governance.core.lock.listener;

import lombok.SneakyThrows;
import org.apache.shardingsphere.governance.repository.api.RegistryRepository;
import org.apache.shardingsphere.governance.repository.api.listener.DataChangedEvent.Type;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.lang.reflect.Field;

import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public final class LockListenerManagerTest {
    
    @Mock
    private RegistryRepository registryRepository;
    
    @Mock
    private GlobalLockChangedListener globalLockChangedListener;
    
    @Test
    public void assertInitListeners() {
        LockListenerManager actual = new LockListenerManager(registryRepository);
        setField(actual, "globalLockChangedListener", globalLockChangedListener);
        actual.initListeners();
        verify(globalLockChangedListener).watch(Type.ADDED);
    }
    
    @SneakyThrows(ReflectiveOperationException.class)
    private static void setField(final Object target, final String fieldName, final Object fieldValue) {
        Field field = target.getClass().getDeclaredField(fieldName);
        field.setAccessible(true);
        field.set(target, fieldValue);
    }
}
