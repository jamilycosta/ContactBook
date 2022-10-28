package controller;

import java.net.URL;
import java.util.ResourceBundle;
import application.Main.Chave;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.PasswordField;
import model.UsuarioDAO;
import javafx.fxml.Initializable;

public class LoginController implements Initializable {
	
	@FXML
    private TextField cpfUsuarioLogin;

    @FXML
    private PasswordField senhaUsuarioLogin;
    
    private UsuarioDAO dao;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		dao = new UsuarioDAO();
	}
	
	@FXML
    void entrarNoSistema(ActionEvent event) {
		if(buscaUsuario() == 1) {
			try {
				Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
				Parent root3 = FXMLLoader.load(getClass().getResource("/view/Contatos.fxml"));
				Scene scene = new Scene(root3);
				
				stage.setScene(scene);
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			exibirDialogoErro("Usuário inválido!\nAcesso negado!");
		}
    }
	
	@FXML
    void acessarTelaCadastro(ActionEvent event) {
		try {
			Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
			Parent root2 = FXMLLoader.load(getClass().getResource("/view/Cadastro.fxml"));
			Scene scene = new Scene(root2);
			
			stage.setScene(scene);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
    
    private void exibirDialogoErro(String erro) {
    	Alert alert = new Alert(AlertType.ERROR);
    	alert.setTitle("Informação");
    	alert.setHeaderText(null);
    	alert.setContentText(erro);
    	
    	alert.showAndWait();
    }
    
    private int buscaUsuario() {
    	
    	try {
    		Chave.setChave(dao.consultar(cpfUsuarioLogin.getText(), senhaUsuarioLogin.getText()));
    		
			if(Chave.getChave().equals(cpfUsuarioLogin.getText())) {
				return 1;
			} else {
				return 0;
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
    	
    	return 0;
    }

}
