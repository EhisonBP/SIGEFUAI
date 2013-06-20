/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ve.co.bsc.util;

import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author jdeoliveira
 */
public class ListDecorator<T> {

    private List<T> elements;

    public ListDecorator(List<T> _elements) {
        this.elements = _elements;
    }

    public List<T> getElements() {
        return elements;
    }

    public void setElements(List<T> elements) {
        this.elements = elements;
    }

    public int getSize() {
        return this.elements.size();
    }

}
