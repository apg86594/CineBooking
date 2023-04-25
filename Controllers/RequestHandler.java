import java.sql.Connection;
import java.sql.SQLException;
import java.io.*;

public class RequestHandler {
    final String secretKey = "ylwqc";
    SendEmail email = new SendEmail();

    public String handleRequest(String message, Connection connection) throws IOException, SQLException {
        String[] inputs = message.split(",", -2);
        String command = inputs[0];
        if (command.equals("REGISTER")) {
            registerUser register = new registerUser();
            message = register.registerUserEx(inputs, connection);
        } else if (command.equals("LOGIN")) {
            loginUser login = new loginUser();
            message = login.loginUserEx(inputs, connection);
        } else if (command.equals("EDIT")) {
            editUser edit = new editUser();
            message = edit.editUserEx(inputs, connection);
        } else if (command.equals("CONFIRM")) {
            confirmation confirm = new confirmation();
            message = confirm.confirmationEx(inputs, connection);
        } else if (command.equals("REQUESTFORGOTPW")) {
            requestForgotPW request = new requestForgotPW();
            message = request.requestForgotPWEx(inputs, connection);
        } else if (command.equals("SUBMITFORGOTPW")) {
            submitForgotPW submit = new submitForgotPW();
            message = submit.submitForgotPWEx(inputs, connection);
        } else if (command.equals("LOGOUT")) {
            logout logout = new logout();
            message = logout.logoutEx();
        } else if (command.equals("SCHEDULEMOVIE")) {
            scheduleMovie schedule = new scheduleMovie();
            message = schedule.scheduleMovieEx(inputs, connection);
        } else if (command.equals("ADDMOVIE")) {
            addMovie add = new addMovie();
            message = add.addMovieEx(inputs, connection);
        } else if (command.equals("SEARCHGENRE")) {
            searchByGenre genreSearch = new searchByGenre();
            message = genreSearch.searchByGenreEx(inputs, connection);
        } else if (command.equals("SEARCHINPUT")) {
            searchByInput inputSearch = new searchByInput();
            message = inputSearch.searchByInputExString(inputs, connection);
        } else if (command.equals("GETMOVIETIMES")) {
            viewTimes viewTimes = new viewTimes();
            message = viewTimes.timeAndPlace(inputs,connection);
        } else if (command.equals("GETMOVIES")) {
            getMovies getMovies = new getMovies();
            message = getMovies.getMoviesEx(connection);
        } else if (command.equals("ADDPROMOTION")) {
            addPromotion addPromotion = new addPromotion();
            message = addPromotion.addPromotionEx(inputs, connection);
        } else if (command.equals("GETPROMOTION")) {
            getPromotion getPromotion = new getPromotion();
            message = getPromotion.getPromotionEx(connection);
        } else if (command.equals("SENDPROMOTION")) {
            sendPromotion sendPromotion = new sendPromotion();
            message = sendPromotion.sendPromotionEx(inputs, connection);
        } else if (command.equals("DELETEMOVIE")) {
            deleteMovie deleteMovie = new deleteMovie();
            message = deleteMovie.deleteMovieEx(inputs, connection);
        } else if (command.equals("EDITMOVIE")) {
            editMovie editMovie = new editMovie();
            message = editMovie.editMovieEx(inputs, connection);
        } else if (command.equals("GETSHOWTIMES")) {
            getShowtimes getShowtimes = new getShowtimes();
            message = getShowtimes.getShowtimesEx(connection);
        } else if (command.equals("GETAUDITORIUMS")) {
            getAuditoriums getAuditoriums = new getAuditoriums();
            message = getAuditoriums.getAuditoriumsEx(connection);
        } else if (command.equals("GETAUDITORIUMSEATS")) {
            getAuditoriumSeats getAuditoriumSeats = new getAuditoriumSeats();
            message = getAuditoriumSeats.getAuditoriumSeatsEx(inputs, connection);
        } else if (command.equals("GETORDERHISTORY")) {
            getOrderHistory getOrderHistory = new getOrderHistory();
            message = getOrderHistory.getOrdersEx(inputs, connection);
        }
        return message;
    } // handleRequest


} // RequestHandler

