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
public class Signin extends HttpServlet {

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
            out.println("<title>Servlet Signin</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet Signin at " + request.getContextPath() + "</h1>");
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
        String userid =  receivedJSON.getString("userid");
        String password =  receivedJSON.getString("password");
//        String type = receivedJSON.getString("type");
        System.out.println("recieved data "+userid+" password "+password);
        if(userid != null && password != null && !userid.trim().equalsIgnoreCase("")
                && !password.trim().equalsIgnoreCase("")) {
            try {
                Statement statment = ConnectDatabase.connector().createStatement();
                ResultSet result;
                
                   result = statment.executeQuery("select *from userInfo where username = '"+userid+"' and password = '"+password+"'");
                
                
                 
                if(result.next()) {
                    responseJson.accumulate("status", "success");
                    responseJson.accumulate("msg",  "authentication successful");
                    
                }else {
                     responseJson.accumulate("status", "fail");
                    responseJson.accumulate("msg",  "Invalid username and password");
                     
                }
            } catch (SQLException ex) {
                Logger.getLogger(Signin.class.getName()).log(Level.SEVERE, null, ex);
                 responseJson.accumulate("status", "fail");
                    responseJson.accumulate("msg",  "Internal error");
                
            }
        }else {
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
