package org.choose_a_name.main.internal;

public interface IControllable<T extends IControllable<T>> {
 Controller<T> getController();
}
