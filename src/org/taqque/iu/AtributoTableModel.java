/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.taqque.iu;

import java.util.ArrayList;
import java.util.List;
import javax.swing.table.AbstractTableModel;
import org.taqque.almacenamiento.Atributo;

/**
 *
 * @author Usuario38
 */
public class AtributoTableModel extends AbstractTableModel {

    private String[] columnNames = {"Nombre", "Tipo"};
    private List<Atributo> listaAtributos = new ArrayList<Atributo>();

    @Override
    public int getRowCount() {
        return listaAtributos.size();
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    @Override
    public void setValueAt(Object value, int rowIndex, int columnIndex) {
        Atributo atributo = listaAtributos.get(rowIndex);

        switch (columnIndex) {
            case 0:
                atributo.setNombre((String) value);
                break;
            case 1:
                atributo.setTipo((Class<? extends Comparable>) value);
                break;
        }
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Object returnValue = null;
        Atributo atributo = listaAtributos.get(rowIndex);

        switch (columnIndex) {
            case 0:
                returnValue = atributo.getNombre();
                break;
            case 1:
                returnValue = atributo.getTipo();
                break;
        }
        return returnValue;
    }

    @Override
    public String getColumnName(int column) {
        return columnNames[column];
    }

    @Override
    public boolean isCellEditable(int row, int column) {
        return true;
    }

    public void addRow(Object data) {
        this.listaAtributos.add((Atributo) data);
        fireTableDataChanged();
    }

    public Atributo getRow(int index) {
        return listaAtributos.get(index);
    }

    public void limpiarModeloTabla() {
        for (int i = this.getRowCount(); --i >= 0;) {
            this.listaAtributos.clear();
        }
        fireTableDataChanged();
    }
    public List<Atributo> getListaAtributos()
    {
        return this.listaAtributos;
    }
}
