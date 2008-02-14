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


import org.objectweb.asm.*;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Esko Luontola
 * @since 14.2.2008
 */
public class AsmLineNumberStrategy implements LineNumberStrategy {

    public int firstLineNumber(Class<?> clazz, int defaultValue) {
        return 0;
    }

    public int firstLineNumber(Method method, int defaultValue) {
        try {
            ClassReader reader = new ClassReader(method.getDeclaringClass().getName());
            LineNumberClassVisitor visitor = new LineNumberClassVisitor();
            reader.accept(visitor, false);
            int line = visitor.getLineNumber(method);
            return line >= 0 ? line : defaultValue;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private class LineNumberClassVisitor extends NullClassVisitor {

        private final LineNumberCodeVisitor codeVisitor = new LineNumberCodeVisitor(this);
        private final Map<String, Integer> methodLines = new HashMap<String, Integer>();
        private String nextMethod;

        public CodeVisitor visitMethod(int access, String name, String desc, String[] exceptions, Attribute attrs) {
            nextMethod = name;
            return codeVisitor;
        }

        public int getLineNumber(Method method) {
            return methodLines.get(method.getName());
        }

        public void visitLineNumber(int line) {
            if (nextMethod != null) {
                methodLines.put(nextMethod, line);
                nextMethod = null;
            }
        }
    }

    private class LineNumberCodeVisitor extends NullCodeVisitor {

        private final LineNumberClassVisitor classVisitor;

        public LineNumberCodeVisitor(LineNumberClassVisitor classVisitor) {
            this.classVisitor = classVisitor;
        }

        public void visitLineNumber(int line, Label start) {
            classVisitor.visitLineNumber(line);
        }
    }

    private static abstract class NullClassVisitor implements ClassVisitor {

        public void visit(int version, int access, String name, String superName, String[] interfaces, String sourceFile) {
        }

        public void visitInnerClass(String name, String outerName, String innerName, int access) {
        }

        public void visitField(int access, String name, String desc, Object value, Attribute attrs) {
        }

        public CodeVisitor visitMethod(int access, String name, String desc, String[] exceptions, Attribute attrs) {
            return null;
        }

        public void visitAttribute(Attribute attr) {
        }

        public void visitEnd() {
        }
    }

    private static abstract class NullCodeVisitor implements CodeVisitor {

        public void visitInsn(int opcode) {
        }

        public void visitIntInsn(int opcode, int operand) {
        }

        public void visitVarInsn(int opcode, int var) {
        }

        public void visitTypeInsn(int opcode, String desc) {
        }

        public void visitFieldInsn(int opcode, String owner, String name, String desc) {
        }

        public void visitMethodInsn(int opcode, String owner, String name, String desc) {
        }

        public void visitJumpInsn(int opcode, Label label) {
        }

        public void visitLabel(Label label) {
        }

        public void visitLdcInsn(Object cst) {
        }

        public void visitIincInsn(int var, int increment) {
        }

        public void visitTableSwitchInsn(int min, int max, Label dflt, Label labels[]) {
        }

        public void visitLookupSwitchInsn(Label dflt, int keys[], Label labels[]) {
        }

        public void visitMultiANewArrayInsn(String desc, int dims) {
        }

        public void visitTryCatchBlock(Label start, Label end, Label handler, String type) {
        }

        public void visitMaxs(int maxStack, int maxLocals) {
        }

        public void visitLocalVariable(String name, String desc, Label start, Label end, int index) {
        }

        public void visitLineNumber(int line, Label start) {
        }

        public void visitAttribute(Attribute attr) {
        }
    }
}
