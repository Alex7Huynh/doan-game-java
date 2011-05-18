/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mypackage;

/**
 *
 * @author Admin
 */
public class Card {

    private int _ID;
    private int _so;
    private int _loai;
    private int _diem;
    private String _hinh;

    /**
     * @return the _ID
     */
    public int getID() {
        return _ID;
    }

    /**
     * @param ID the _ID to set
     */
    public void setID(int ID) {
        this._ID = ID;
    }

    /**
     * @return the _so
     */
    public int getSo() {
        return _so;
    }

    /**
     * @param so the _so to set
     */
    public void setSo(int so) {
        this._so = so;
    }

    /**
     * @return the _loai
     */
    public int getLoai() {
        return _loai;
    }

    /**
     * @param loai the _loai to set
     */
    public void setLoai(int loai) {
        this._loai = loai;
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
     * @return the _hinh
     */
    public String getHinh() {
        return _hinh;
    }

    /**
     * @param hinh the _hinh to set
     */
    public void setHinh(String hinh) {
        this._hinh = hinh;
    }

    public Card() {
        _ID = 0;
        _so = 0;
        _loai = 0;
        _diem = 0;
        _hinh = "";
    }

    public Card(int ID, int So, int Loai, int Diem, String Hinh) {
        _ID = ID;
        _so = So;
        _loai = Loai;
        _diem = Diem;
        _hinh = Hinh;
    }
}
