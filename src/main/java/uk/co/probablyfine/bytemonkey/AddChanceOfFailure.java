package uk.co.probablyfine.bytemonkey;

import java.util.Random;

import jdk.internal.org.objectweb.asm.Opcodes;
import jdk.internal.org.objectweb.asm.tree.FrameNode;
import jdk.internal.org.objectweb.asm.tree.InsnList;
import jdk.internal.org.objectweb.asm.tree.JumpInsnNode;
import jdk.internal.org.objectweb.asm.tree.LabelNode;
import jdk.internal.org.objectweb.asm.tree.LdcInsnNode;
import jdk.internal.org.objectweb.asm.tree.MethodInsnNode;

public class AddChanceOfFailure {

    private static final Random random = new Random();
    private static boolean onlyOnce = true;

    public InsnList apply(InsnList newInstructions, double chanceOfFailure) {
        final InsnList list = new InsnList();

        final LabelNode originalCodeLabel = new LabelNode();

        list.add(new LdcInsnNode(chanceOfFailure));
        list.add(new MethodInsnNode(
            Opcodes.INVOKESTATIC,
            "uk/co/probablyfine/bytemonkey/AddChanceOfFailure",
            "shouldActivate",
            "(D)Z",
            false // this is not a method on an interface
        ));

        list.add(new JumpInsnNode(Opcodes.IFEQ, originalCodeLabel));

        list.add(newInstructions);

        list.add(new FrameNode(
            Opcodes.F_APPEND,   // append to the last stack frame
            0, new Object[] {}, // no local variables here
            0, new Object[] {}  // no stack either!
        ));

        list.add(originalCodeLabel);

        return list;
    }

    public static boolean shouldActivate(double chanceOfFailure) {
        final boolean flag = random.nextDouble() < chanceOfFailure;
        if(flag && onlyOnce){
            System.err.println("BYTEMONKEY FAULT INJECTOR");
            System.err.println("The variable{onlyOnce} is " + onlyOnce);
            onlyOnce = false;
            System.err.println("A fault occured at " + System.nanoTime());
        } 
        return flag && onlyOnce;
    }
}
