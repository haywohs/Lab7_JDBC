<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Users</title>
    </head>
    <style>
        .whole{
            display:flex;
            justify-content: center;
        }
        .addNew{
            flex:1;
            border: 2px brown solid;
        }
        .display{
            flex:1;
            border: 2px blue solid;
        }
        .update{
            flex:1;
            border:2px brown solid;
        }
    </style>
    <body>
        <div class="whole">
            <div class="addNew">
                <h3>Add User</h3>
                <form method="post" action="user">
                    <table>
                        <tr><td><input type="email" name="email" placeholder="Email"></td></tr>
                        <tr><td><input type="text" name="first_name" placeholder="First Name"></td></tr>
                        <tr><td><input type="text" name="last_name" placeholder="Last Name"></td></tr>
                        <tr><td><input type="text" name="password" placeholder="Password"></td></tr>
                        <tr><td><select name="rolename">
                                    <c:forEach var="role" items="${roles}">
                                        <option>${role.role_name}</option>
                                    </c:forEach>
                                </select>
                            </td>
                        </tr>
                        <tr><td><input type="submit" value="Save"></td></tr>
                        <input type="hidden" name="save" value="add">
                    </table>
                </form>
            </div>
            <div class="display">
                <h3>Manage Users</h3>
                <table>
                    <tr>
                        <th>Email</th>
                        <th>First Name</th>
                        <th>Lsat Name</th>
                        <th>Role</th>
                        <th>Edit</th>
                        <th>Delete</th>
                    </tr>
                    <c:forEach var="user" items="${users}">
                        <tr><td>${user.email}</td>
                            <td>${user.first_name}</td>
                            <td>${user.last_name}</td>
                            <c:forEach items="${roles}" var="role">
                                <c:if test="${role.role_id==user.role}">
                                    <td>${user.role}</td>
                                </c:if>
                            </c:forEach>
                            <td><a href="user?action=edit&amp;email=${user.email}">Edit</a></td>
                            <td><a href="user?action=delete&amp;email=${user.email}">Delete</a></td>
                        </tr>
                    </c:forEach>
                </table>
            </div>
            <div class="update">
                <h3>Edit User</h3>
                <form method="post" action="user">
                    <table>
                        <tr><td><input type="text" name="e_firstname" value="${editFirst}"></td></tr>
                        <tr><td><input type="text" name="e_lastname" value="${editLast}"></td></tr>
                        <tr><td><input type="text" name="e_password" value="${editPassword}"></td></tr>
                        <tr><td><select name="editRole">
                                    <c:forEach var="role" items="${roles}">
                                        <c:choose>
                                            <c:when test="${role.role_name == editRole}">
                                                <option selected>${role.role_name}</option>
                                            </c:when>
                                            <c:otherwise>
                                                <option>${role.role_name}</option>
                                            </c:otherwise>
                                        </c:choose>
                                    </c:forEach>
                                </select>
                            </td>
                        </tr>
                        <tr><td><input type="submit" value="Update"></td></tr>
                        <input type="hidden" name="action" value="update">
                    </table>
                </form>
            </div>
        </div>
    </body>
</html>
