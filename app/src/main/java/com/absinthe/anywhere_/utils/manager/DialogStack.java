package com.absinthe.anywhere_.utils.manager;

import android.app.Dialog;

import androidx.fragment.app.DialogFragment;

import java.util.Objects;
import java.util.Stack;

/**
 * Created by Absinthe at 2020/1/13
 * <p>
 * Dialog 栈，使同一时期只显示一个 Dialog 以保证优雅的显示效果
 */
public class DialogStack {

    public enum Singleton {
        INSTANCE;
        private Stack<Object> instance;

        Singleton() {
            instance = new Stack<>();
        }

        public Stack<Object> getInstance() {
            return instance;
        }
    }

    public static void push(Object dialog) {
        printStack();
        if (!(dialog instanceof Dialog) && !(dialog instanceof DialogFragment)) {
            return;
        }

        if (Singleton.INSTANCE.getInstance().empty()) {
            Singleton.INSTANCE.getInstance().push(dialog);
        } else {
            Object peekObject = Singleton.INSTANCE.getInstance().peek();

            if (peekObject instanceof Dialog) {
                ((Dialog) peekObject).dismiss();
            } else if (peekObject instanceof DialogFragment) {
                Objects.requireNonNull(
                        ((DialogFragment) peekObject).getDialog()).hide();
            }
            Singleton.INSTANCE.getInstance().push(dialog);
        }

        if (dialog instanceof Dialog) {
            ((Dialog) dialog).show();
        }
        printStack();
    }

    public static void pop() {
        printStack();
        if (Singleton.INSTANCE.getInstance().empty()) {
            return;
        }

        Object peekObject = Singleton.INSTANCE.getInstance().peek();

        if (peekObject instanceof Dialog) {
            ((Dialog) peekObject).dismiss();
        } else if (peekObject instanceof DialogFragment) {
            ((DialogFragment) peekObject).dismiss();
        }
        Singleton.INSTANCE.getInstance().pop();

        if (!Singleton.INSTANCE.getInstance().empty()) {
            peekObject = Singleton.INSTANCE.getInstance().peek();

            if (peekObject instanceof Dialog) {
                ((Dialog) peekObject).show();
            } else if (peekObject instanceof DialogFragment) {
                Objects.requireNonNull(
                        ((DialogFragment) peekObject).getDialog()).show();
            }
        }
        printStack();
    }

    public static void printStack() {
        Logger.i("DialogStack:");

        for (Object object : Singleton.INSTANCE.getInstance()) {
            Logger.i(object.getClass());
        }

        Logger.i("--------------------------------------");
    }
}
