/*
 * 0812508-0812515-0812527
 */
package mypackage;

import java.util.ArrayList;

/**
 *
 * @author Admin
 */
public class Player {

    private ArrayList<String> _boBai;
    private int _diem;
    private boolean _duocDanh;
    private String _ten;

    /**
     * @return the _boBai
     */
    public ArrayList<String> getBoBai() {
        return _boBai;
    }

    /**
     * @param boBai the _boBai to set
     */
    public void setBoBai(ArrayList<String> boBai) {
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

    /**
     * @Khởi tạo mặc định
     */
    public Player() {
        _boBai = new ArrayList<String>(13);
        _diem = 0;
        _duocDanh = false;
        _ten = "";
    }

    /**
     * @Khởi tạo mặc định có tham số
     */
    public Player(int Diem, boolean DuocDanh, String Ten) {
        _boBai = new ArrayList<String>(13);
        _diem = Diem;
        _duocDanh = DuocDanh;
        _ten = Ten;
    }

    /**
     * @Lấy số của lá bài (từ 1 đến 14)
     */
    public static int LaySo(String LaBai) {
        //Bỏ file extension
        String tmp = LaBai.substring(0, LaBai.length() - 4);
        //Tách số và loại
        String[] chuoi = tmp.split("_");
        return Integer.parseInt(chuoi[0]);
    }

    /**
     * @Lấy điểm của lá bài (0, 1 hoặc 13)
     */
    public static int LayDiem(String LaBai) {
        //Bỏ file extension
        String tmp = LaBai.substring(0, LaBai.length() - 4);
        //Tách số và loại
        String[] chuoi = tmp.split("_");
        //Tính điểm sẽ bị cộng
        if ("Co".equals(chuoi[1])) {
            return 1;
        } else if ("12".equals(chuoi[0]) && "Bich".equals(chuoi[1])) {
            return 13;
        } else {
            return 0;
        }
    }

    /**
     * @Lấy số chỉ loại của lá bài (Co-Ro-Chuon-Bich)
     */
    public static int LayLoai(String LaBai) {
        //Bỏ file extension
        String tmp = LaBai.substring(0, LaBai.length() - 4);
        //Tách số và loại
        String[] chuoi = tmp.split("_");
        //Trả về loại bài
        if ("Co".equals(chuoi[1])) {
            return 0;
        }
        if ("Ro".equals(chuoi[1])) {
            return 1;
        }
        if ("Chuon".equals(chuoi[1])) {
            return 2;
        }
        if ("Bich".equals(chuoi[1])) {
            return 3;
        }

        return -1;
    }

    /**
     * @So sánh 4 lá bài để xem bên nào sẽ gom bài
     */
    public static int SoSanhBai(String first, String second, String third, String fourth) {
        int max = Player.LaySo(first);
        int idx = 0;
        //Kiểm tra điều kiện cùng loại rồi mới so sánh số
        if (Player.LayLoai(second) == Player.LayLoai(first)
                && Player.LaySo(second) > max) {
            max = Player.LaySo(first);
            idx = 1;
        }
        if (Player.LayLoai(third) == Player.LayLoai(first)
                && Player.LaySo(third) > max) {
            max = Player.LaySo(third);
            idx = 2;
        }
        if (Player.LayLoai(fourth) == Player.LayLoai(first)
                && Player.LaySo(fourth) > max) {
            max = Player.LaySo(fourth);
            idx = 3;
        }
        return idx;
    }
    
    public static String TaoLaBai(int So, int Loai)
    {
        String result = "";
        if(Loai == 0)
            result = So + "_Co" + ".png";
        else if(Loai == 1)
            result = So + "_Ro" + ".png";
        else if(Loai == 2)
            result = So + "_Chuon" + ".png";
        else if(Loai == 3)
            result = So + "_Bich" + ".png";
        return result;
    }
}
