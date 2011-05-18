/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mypackage;

import java.util.ArrayList;

/**
 *
 * @author Admin
 */
public class Player {

    private ArrayList<Card> _boBai;
    private int _diem;
    private boolean _duocDanh;
    private String _ten;

    /**
     * @return the _boBai
     */
    public ArrayList<Card> getBoBai() {
        return _boBai;
    }

    /**
     * @param boBai the _boBai to set
     */
    public void setBoBai(ArrayList<Card> boBai) {
        this._boBai = boBai;
    }

    /**
     * @return the _diem
     */
    public int getDiem() {
        return _diem;
    }

    /**
     * @param diem the _diem to set
     */
    public void setDiem(int diem) {
        this._diem = diem;
    }

    /**
     * @return the _duocDanh
     */
    public boolean isDuocDanh() {
        return _duocDanh;
    }

    /**
     * @param duocDanh the _duocDanh to set
     */
    public void setDuocDanh(boolean duocDanh) {
        this._duocDanh = duocDanh;
    }

    /**
     * @return the _ten
     */
    public String getTen() {
        return _ten;
    }

    /**
     * @param ten the _ten to set
     */
    public void setTen(String ten) {
        this._ten = ten;
    }
    
    public Player() {
        _boBai = new ArrayList<Card>(13);
        _diem = 0;
        _duocDanh = false;
        _ten = "";
    }

    public Player(int Diem, boolean DuocDanh, String Ten) {
        _boBai = new ArrayList<Card>(13);
        _diem = Diem;
        _duocDanh = DuocDanh;
        _ten = Ten;
    }
}
