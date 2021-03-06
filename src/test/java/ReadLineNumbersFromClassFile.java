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

import net.sf.cglib.asm.*;
import org.apache.bcel.classfile.ClassParser;
import org.apache.bcel.classfile.JavaClass;
import org.apache.bcel.classfile.LineNumberTable;
import org.apache.bcel.classfile.Method;
import org.apache.bcel.util.ClassLoaderRepository;

import java.io.IOException;
import java.util.Arrays;

public class ReadLineNumbersFromClassFile {

    private static final boolean READ_PHYSICAL_FILE = false;

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        JavaClass javaClass;

        if (READ_PHYSICAL_FILE) {
            ClassParser parser = new ClassParser("target/test-classes/ReadLineNumbersFromClassFile.class");
            javaClass = parser.parse();
        } else {
            ClassLoaderRepository repository = new ClassLoaderRepository(ReadLineNumbersFromClassFile.class.getClassLoader());
            javaClass = repository.loadClass(ReadLineNumbersFromClassFile.class.getName());
        }
        System.out.println("javaClass = " + javaClass);

        for (Method method : javaClass.getMethods()) {
            System.out.println("----------------");
            LineNumberTable table = method.getLineNumberTable();
            System.out.println("Method = " + method.getName());
            System.out.println("LineNumberTable = " + Arrays.toString(table.getLineNumberTable()));
            System.out.println("Code = " + method.getCode());
        }

        System.out.println("----------------");
        ClassReader reader = new ClassReader(ReadLineNumbersFromClassFile.class.getName());
        reader.accept(new ClassVisitor() {
            public void visit(int i, int i1, String s, String s1, String[] strings, String s2) {
            }

            public void visitInnerClass(String s, String s1, String s2, int i) {
            }

            public void visitField(int i, String s, String s1, Object o, Attribute attribute) {
            }

            public CodeVisitor visitMethod(int i, String s, String s1, String[] strings, Attribute attribute) {
                System.out.println("ReadLineNumbersFromClassFile.visitMethod");
                System.out.println("i = " + i);
                System.out.println("s = " + s);
                System.out.println("s1 = " + s1);
                System.out.println("strings = " + Arrays.toString(strings));
                System.out.println("attribute = " + attribute);
                return new CodeVisitor() {
                    public void visitInsn(int i) {
                    }

                    public void visitIntInsn(int i, int i1) {
                    }

                    public void visitVarInsn(int i, int i1) {
                    }

                    public void visitTypeInsn(int i, String s) {
                    }

                    public void visitFieldInsn(int i, String s, String s1, String s2) {
                    }

                    public void visitMethodInsn(int i, String s, String s1, String s2) {
                    }

                    public void visitJumpInsn(int i, Label label) {
                    }

                    public void visitLabel(Label label) {
                    }

                    public void visitLdcInsn(Object o) {
                    }

                    public void visitIincInsn(int i, int i1) {
                    }

                    public void visitTableSwitchInsn(int i, int i1, Label label, Label[] labels) {
                    }

                    public void visitLookupSwitchInsn(Label label, int[] ints, Label[] labels) {
                    }

                    public void visitMultiANewArrayInsn(String s, int i) {
                    }

                    public void visitTryCatchBlock(Label label, Label label1, Label label2, String s) {
                    }

                    public void visitMaxs(int i, int i1) {
                    }

                    public void visitLocalVariable(String s, String s1, Label label, Label label1, int i) {
                    }

                    public void visitLineNumber(int i, Label label) {
                        System.out.println("ReadLineNumbersFromClassFile.visitLineNumber");
                        System.out.println("i = " + i);
                        System.out.println("label = " + label);
                    }

                    public void visitAttribute(Attribute attribute) {
                    }
                };
            }

            public void visitAttribute(Attribute attribute) {
            }

            public void visitEnd() {
            }
        }, false);
    }

    public void empty() {
    }

    // Byte code instrumentation libraries:
    //
    // BCEL: http://jakarta.apache.org/bcel/
    // + gives direct access to line number table, as shown in code above
    // http://repo1.maven.org/maven2/org/apache/bcel/bcel/5.2/
    // bcel-5.2.jar   521K
    //
    // Javassist: http://www.csg.is.titech.ac.jp/~chiba/javassist/
    // + yes: javassist.bytecode.MethodInfo.getLineNumber
    // http://repo1.maven.org/maven2/jboss/javassist/3.6.ga/
    // javassist-3.6.ga.jar   530K
    //
    // ASM: http://asm.objectweb.org/
    // - does not give easy access to line numbers
    //
    // CGLIB: http://cglib.sourceforge.net/
    // - nope, only info on method signatures etc.
    //
    // More related programs: http://aopalliance.sourceforge.net/motivations.html
}
