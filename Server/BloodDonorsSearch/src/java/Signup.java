/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.sf.json.JSONObject;

/**
 *
 * @author pavankumar
 */
public class Signup extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet Signup</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet Signup at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
//        processRequest(request, response);
  String requestString = ReadData.readData(request);
        System.out.println("received data "+requestString);
        JSONObject receivedJSON = JSONObject.fromObject(requestString);
        JSONObject responseJson = new JSONObject();
        String name =  receivedJSON.getString("name");
        String mobileNumber =  receivedJSON.getString("phoneNumber");
        String password =  receivedJSON.getString("password");
        String userName =  receivedJSON.getString("userName");
        String email =  receivedJSON.getString("email");
        String door =  receivedJSON.getString("door");
        String city =  receivedJSON.getString("city");
        String zip =  receivedJSON.getString("zip");
        String bloodGroup =  receivedJSON.getString("bloodGroup");
        String milesToTravel =  receivedJSON.getString("milesToTravel");
        String gender =  receivedJSON.getString("gender");
        
        
        
//        System.out.println("recieved data "+userid+" password "+password);
       
            try {
                Statement statment = ConnectDatabase.connector().createStatement();
                int result = statment.executeUpdate("insert into userInfo(name,email,gender,phonenumber,dooraddress,cityaddress,zip,bloodgroup,username,password,mileswillingtotravel) values('"+name+"','"+email+"','"+gender+"','"+mobileNumber+"','"+door+"','"+city+"','"+zip+"','"+bloodGroup+"','"+userName+"','"+password+"','"+milesToTravel+"')");
                if(result > 0) {
                    responseJson.accumulate("status", "success");
                    responseJson.accumulate("msg",  "authentication successful");
                }else {
                      responseJson.accumulate("status", "fail");
                    responseJson.accumulate("msg",  "Invalid username and password");
                }
            } catch (SQLException ex) {
                Logger.getLogger(Signup.class.getName()).log(Level.SEVERE, null, ex);
                 responseJson.accumulate("status", "fail");
                    responseJson.accumulate("msg",  "Internal error");
                
            }
        
        PrintWriter out = response.getWriter();
        out.println(responseJson.toString());
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
