/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 *
 * @author pavankumar
 */
public class Searchdonors extends HttpServlet {

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
            out.println("<title>Servlet Searchdonors</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet Searchdonors at " + request.getContextPath() + "</h1>");
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
//        JSONObject tt;
//        tt = JSONObject.fromObject({"\"GlossSee\": \"markup\"");
//        tt = new JSONObject("{\"GlossSee\": \"markup\"}");
        
        JSONObject responseJson = new JSONObject();
        
        JSONObject receivedJSON = JSONObject.fromObject(requestString);
//        String searchString = receivedJSON.getString("searchString");
//        String bloodGRP = receivedJSON.getString("bloodGRP");
        String searchBy = receivedJSON.getString("searchBy");
        String username = receivedJSON.getString("username");
       // String postalcode = receivedJSON.getString("postalcode");
        boolean search = false;
        if(searchBy.equalsIgnoreCase("std")) {
            search = true;
        }
//        JSONObject responseJson = new JSONObject();
//        String userid =  receivedJSON.getString("userid");
//        String password =  receivedJSON.getString("password");
//        
//        System.out.println("recieved data "+userid+" password "+password);
//        if(userid != null && password != null && !userid.trim().equalsIgnoreCase("")
//                && !password.trim().equalsIgnoreCase("")) {
            try {
                Statement statment = ConnectDatabase.connector().createStatement();
                ResultSet result;
//                Calendar now = Calendar.getInstance();
//                now.add(Calendar.MONTH, -3);
//                Date beforethreeMonths = now.getTime();
                JSONArray responseArray = new JSONArray();
                Statement statment1 = ConnectDatabase.connector().createStatement();
                ResultSet result1 = statment1.executeQuery("select *from userInfo where bloodgroup='"+searchBy+"' and username <> '"+username+"'");
                
                while(result1.next()) {
                    
                    JSONObject donar = new JSONObject();
                    donar.accumulate("name", result1.getString("name"));
                    donar.accumulate("mobilenumber", result1.getString("phonenumber"));
                    donar.accumulate("bloodgroup", result1.getString("bloodgroup"));
                    donar.accumulate("address", result1.getString("dooraddress")+","+result1.getString("cityaddress")+","+result1.getString("zip"));
                    responseArray.add(donar);
                    
                }
                
//                if(search) {
//                    result = statment.executeQuery("select *from DonarData where STDCode='"+searchString+"' and BloodGrp = '"+bloodGRP+"'");
//                }else {
//                    result = statment.executeQuery("select *from DonarData where City='"+searchString+"' and BloodGrp = '"+bloodGRP+"'");
//                }
                
                
                
                if(responseArray.size() > 0) {
                    responseJson.accumulate("status", "success");
                    
                    responseJson.accumulate("donars", responseArray);
                    
                }else {
                     responseJson.accumulate("status", "success");
                    responseJson.accumulate("msg",  "No donars found");
                     
                }
            } catch (SQLException ex) {
                Logger.getLogger(Searchdonors.class.getName()).log(Level.SEVERE, null, ex);
                 responseJson.accumulate("status", "fail");
                    responseJson.accumulate("msg",  "Internal error");
                
            } 
//            catch (ParseException ex) {
//            Logger.getLogger(Searchdonors.class.getName()).log(Level.SEVERE, null, ex);
//        }
        
//        else {
//                responseJson.accumulate("status", "fail");
//                    responseJson.accumulate("msg",  "Internal error");
//        }
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
