package controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.PasswordField;
import javafx.stage.Stage;
import model.Usuario;
import model.UsuarioDAO;
import javafx.fxml.Initializable;

public class CadastroController implements Initializable {
	
	@FXML
    private TextField nomeUsuario;

    @FXML
    private TextField cpfUsuario;

    @FXML
    private TextField emailUsuario;

    @FXML
    private TextField confEmailUsuario;

    @FXML
    private PasswordField senhaUsuario;

    @FXML
    private PasswordField confSenhaUsuario;
    
    private UsuarioDAO dao;
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		dao = new UsuarioDAO();
	}
	
	private void limparCadastroUsuario() {
		nomeUsuario.clear();
		cpfUsuario.clear();
		emailUsuario.clear();
		confEmailUsuario.clear();
		senhaUsuario.clear();
		confSenhaUsuario.clear();
	}
	
	@FXML
    void cadastrarUsuario(ActionEvent event) {
		Usuario usuario = new Usuario();
		
		usuario.setNome(nomeUsuario.getText());
		usuario.setCpf(cpfUsuario.getText());
		usuario.setEmail(emailUsuario.getText());
		usuario.setSenha(senhaUsuario.getText());
		
		try {
			if(verificaDadosNulos() == 0 && verificaEmail() == 0 && verificaSenha() == 0) {
				dao.cadastrar(usuario);
				exibirDialogoInformacao("Cadastro efetuado com sucesso!");
				limparCadastroUsuario();
			}
		} catch (Exception e) {
			exibirDialogoErro("Erro ao cadastrar usuário!");
			e.printStackTrace();
		}
    }
	
	@FXML
    void voltarTelaLogin(ActionEvent event) {
		try {
			Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
			Parent root = FXMLLoader.load(getClass().getResource("/view/Login.fxml"));
			Scene scene = new Scene(root);
			
			stage.setScene(scene);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
	
	private void exibirDialogoInformacao(String informacao) {
    	Alert alert = new Alert(AlertType.INFORMATION);
    	alert.setTitle("Informação");
    	alert.setHeaderText(null);
    	alert.setContentText(informacao);
    	
    	alert.showAndWait();
    }
    
    private void exibirDialogoErro(String erro) {
    	Alert alert = new Alert(AlertType.ERROR);
    	alert.setTitle("Informação");
    	alert.setHeaderText(null);
    	alert.setContentText(erro);
    	
    	alert.showAndWait();
    }
    
    private int verificaSenha() {
    	if(senhaUsuario.getText().equals(confSenhaUsuario.getText())) {
    		return 0;
    	} else {
    		exibirDialogoErro("As senhas digitadas não são compatíveis!");
    		return 1;
    	}
    }
    
    private int verificaEmail() {
    	if(emailUsuario.getText().equals(confEmailUsuario.getText())) {
    		return 0;
    	} else {
    		exibirDialogoErro("Os e-mails digitados não são compatíveis!");
    		return 1;
    	}
    }
    
    private int verificaDadosNulos() {
    	if(nomeUsuario.getText().equals("") || cpfUsuario.getText().equals("") || 
    			senhaUsuario.getText().equals("") || emailUsuario.getText().equals("")) {
    		exibirDialogoErro("Preencha todos os campos!");
    		return 1;
    	} else {
    		return 0;
    	}
    }
    
}
