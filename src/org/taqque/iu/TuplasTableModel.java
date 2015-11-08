/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.taqque.iu;

import java.util.ArrayList;
import java.util.List;
import javax.swing.table.AbstractTableModel;
import org.taqque.almacenamiento.Tupla;

/**
 *
 * @author Usuario38
 */
public class TuplasTableModel extends AbstractTableModel {

    private ArrayList<String> columnNames;
    private List<Tupla> listaTuplas = new ArrayList<Tupla>();
    
    public TuplasTableModel(ArrayList<String> columnNames)
    {
        this.columnNames = columnNames;
    }

    @Override
    public int getRowCount() {
        return listaTuplas.size();
    }

    @Override
    public int getColumnCount() {
        return columnNames.size();
    }

    @Override
    public void setValueAt(Object value, int rowIndex, int columnIndex) {
        Tupla tupla = listaTuplas.get(rowIndex);
        tupla.setValor(columnIndex, (Comparable) value);
        listaTuplas.set(rowIndex, tupla);
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Tupla tupla = listaTuplas.get(rowIndex);
        return tupla.getValor(columnIndex);
    }

    @Override
    public String getColumnName(int column) {
        return columnNames.get(column);
    }

    @Override
    public boolean isCellEditable(int row, int column) {
        return true;
    }

    public void addRow(Object data) {
        this.listaTuplas.add((Tupla) data);
        fireTableDataChanged();
    }

    public Tupla getRow(int index) {
        return listaTuplas.get(index);
    }

    public void limpiarModeloTabla() {
        for (int i = this.getRowCount(); --i >= 0;) {
            this.listaTuplas.clear();
        }
        fireTableDataChanged();
    }
    public List<Tupla> getListaTuplas()
    {
        return this.listaTuplas;
    }
}
