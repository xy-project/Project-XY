package com.projextxy.lib.cofh.gui.element.listbox;

import com.projextxy.lib.cofh.gui.element.ElementListBox;

public interface IListBoxElement {

    public int getHeight();

    public int getWidth();

    public Object getValue();

    public void draw(ElementListBox listBox, int x, int y, int backColor, int textColor);
}
