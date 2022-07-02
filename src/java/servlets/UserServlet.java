package servlets;

import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import models.Role;
import models.User;
import services.RoleService;
import services.UserService;

public class UserServlet extends HttpServlet {
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        UserService us = new UserService();
        RoleService rs = new RoleService();
        List<User> users = null;
        List<Role> roles = null;
        try {
            users = us.getAll();
            roles = rs.getAll();
            request.setAttribute("users", users);
            request.setAttribute("roles", roles);
        } catch (Exception ex) {
            Logger.getLogger(UserServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            String action = request.getParameter("action");
            if (action != null) {
                if (action.equals("delete")) {
                    String email = request.getParameter("email");
                    us.delete(email);
                } else if (action.equals("edit")) {
                    String email = request.getParameter("email");
                    User user = us.get(email);
                    request.setAttribute("editFirst", user.getFirst_name());
                    request.setAttribute("editLast", user.getLast_name());
                    request.setAttribute("editPassword", user.getPassword());
                    request.setAttribute("roles", user.getRole());
                }
            }
        } catch (Exception ex) {
            Logger.getLogger(UserServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
        getServletContext().getRequestDispatcher("/WEB-INF/users.jsp").forward(request, response);
        return;
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        
        UserService us = new UserService();
        RoleService rs = new RoleService();
        String action = request.getParameter("action");
        if (action != null) {
            switch (action) {
                case "add":
                    try {
                        String email = request.getParameter("email");
                        String firstname = request.getParameter("first_name");
                        String lastname = request.getParameter("last_name");
                        String password = request.getParameter("password");
                        String rolename = request.getParameter("rolename");
                        boolean active = request.getParameter("active") != null;
                        
                        List<Role> roles = rs.getAll();
                        int role_id = 1;
                        for (Role role : roles) {
                            if (role.getRole_name().equals(rolename)) {
                                role_id = role.getRole_id();
                            }
                        }
                        
                        us.insert(email, active, firstname, lastname, password, role_id);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
                
                case "update":
                    try {
                        String editEmail = request.getParameter("email");
                        String firstname = request.getParameter("first_name");
                        String lastname = request.getParameter("last_name");
                        String password = request.getParameter("password");
                        String rolename = request.getParameter("rolename");
                        boolean active = request.getParameter("active") != null;
                        
                        List<Role> roles = null;
                        roles = rs.getAll();
                        
                        int role_id = 1;
                        for (Role role : roles) {
                            if (role.getRole_name().equals(rolename)) {
                                role_id = role.getRole_id();
                            }
                        }
                        us.update(editEmail, active, firstname, lastname, password, role_id);
                    } catch (Exception ex) {
                        Logger.getLogger(UserServlet.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    break;
            }
        } else {
            response.sendRedirect("user");
            return;
        }
        getServletContext().getRequestDispatcher("/WEB-INF/users.jsp").forward(request, response);
        return;
    }
    
}
