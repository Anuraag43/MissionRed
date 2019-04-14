
import java.io.BufferedReader;
import javax.servlet.http.HttpServletRequest;


/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author AnveshanReddy
 */
public class ReadData {
    public static String readData(HttpServletRequest request ) {
         StringBuffer jb = new StringBuffer();
  String line = null;
  try {
    BufferedReader reader = request.getReader();
    while ((line = reader.readLine()) != null)
      jb.append(line);
  } catch (Exception e) { /*report an error*/ }
  return jb.toString();
    }
}
