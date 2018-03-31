public class UploadeUtils {
    public static String getPath(String name){
       int  code= name.hashCode();
      int d1= code & 0xf;
      //右移四位
        int code1=code>>>4;
        int d2=code1 & 0xf;
        return "/"+d1+"/"+d2;
    }
}
