/*
 * Class Member Sorter
 * Copyright (c) 2008 Esko Luontola, www.orfjackal.net
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package net.orfjackal.tools.classmembersorter;

import java.lang.reflect.Method;
import java.util.Arrays;

/**
 * Provides lists of class members (methods and inner classes) in the same order
 * as they have been declared in the source code. Requires that the classes have
 * been compiled with debug information (line numbers) included in the bytecode
 * and that the methods have a body (not native nor abstract).
 *
 * @author Esko Luontola
 * @since 4.1.2008
 */
public final class ClassMemberSorter {

    private static LineNumberStrategy strategy = new AsmLineNumberStrategy();

    private ClassMemberSorter() {
    }

    public static void setStrategy(LineNumberStrategy strategy) {
        if (strategy == null) {
            throw new NullPointerException("strategy is null");
        }
        ClassMemberSorter.strategy = strategy;
    }

    /**
     * @return the same as {@link Class#getDeclaredClasses()} but in the same order as declared in the source code.
     */
    public static Class<?>[] getDeclaredClasses(Class<?> declaringClass) {
        Class<?>[] classes = declaringClass.getDeclaredClasses();
        Arrays.sort(classes, new ClassLineNumberComparator(strategy));
        return classes;
    }

    /**
     * @return the same as {@link Class#getDeclaredMethods()} but in the same order as declared in the source code.
     */
    public static Method[] getDeclaredMethods(Class<?> declaringClass) {
        Method[] methods = declaringClass.getDeclaredMethods();
        Arrays.sort(methods, new MethodLineNumberComparator(strategy));
        return methods;
    }

    /**
     * @return the same as {@link Class#getMethods()} but in the same order as declared in the source code.
     */
    public static Method[] getMethods(Class<?> clazz) {
        Method[] methods = clazz.getMethods();
        Arrays.sort(methods, new MethodLineNumberComparator(strategy));
        return methods;
    }
}
