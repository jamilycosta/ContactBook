package controller;

import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import application.Main.Chave;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Contato;
import model.ContatoDAO;

public class ContatosController implements Initializable {
	
	@FXML
    private TabPane abas;

    @FXML
    private Tab consultar;

    @FXML
    private TableView<Contato> contatos;

    @FXML
    private TableColumn<Contato, Integer> id;
    
    @FXML
    private TableColumn<Contato, String> nome;

    @FXML
    private TableColumn<Contato, String> telefone;

    @FXML
    private TableColumn<Contato, String> email;

    @FXML
    private TableColumn<Contato, String> endereco;

    @FXML
    private TextField nomeConsulta;

    @FXML
    private Tab cadastrar;

    @FXML
    private TextField nomeNovoContato;

    @FXML
    private TextField telefoneNovoContato;

    @FXML
    private TextField emailNovoContato;

    @FXML
    private TextField enderecoNovoContato;

    @FXML
    private Tab atualizar;

    @FXML
    private TextField nomeContato;

    @FXML
    private TextField telefoneContato;

    @FXML
    private TextField emailContato;

    @FXML
    private TextField enderecoContato;
    
    private ContatoDAO dao;
    
    private Contato contatoSelecionado;
    
    @Override
	public void initialize(URL arg0, ResourceBundle arg1) {
    	dao = new ContatoDAO();
			
    	id.setCellValueFactory(new PropertyValueFactory<>("id"));
    	nome.setCellValueFactory(new PropertyValueFactory<>("nome"));
    	telefone.setCellValueFactory(new PropertyValueFactory<>("telefone"));
    	email.setCellValueFactory(new PropertyValueFactory<>("email"));
    	endereco.setCellValueFactory(new PropertyValueFactory<>("endereco"));
    	
    	//Inicializa a tabela
    	List<Contato> resultado = dao.consultar(nomeConsulta.getText());
    	if(!resultado.isEmpty()) {
    		consultarContatos();
    	}
	}

    @FXML
    void consultarContatos() {
    	try {
    		List<Contato> resultado = dao.consultar(nomeConsulta.getText());
        	
        	if(resultado.isEmpty()) {
        		contatos.setItems(FXCollections.observableList(resultado));
        		exibirDialogoInformacao("Nenhum contato encontrado!");
        	} else {
        		contatos.setItems(FXCollections.observableList(resultado));
        	}
		} catch (Exception e) {
			exibirDialogoErro("Falha ao realizar consulta!");
			e.printStackTrace();
		}
    }

    @FXML
    void deletarContatos() {
    	if(contatos.getSelectionModel().getSelectedItem() == null) {
    		exibirDialogoErro("Não há nenhum contato selecionado!");
    	} else {
    		if(exibirDialogoConfimacao("Realmente deseja excluir este contato?")) {
    			try {
					dao.deletar(contatos.getSelectionModel().getSelectedItem().getId());
					exibirDialogoInformacao("Contato deletado com sucesso!");
					consultarContatos();
				} catch (Exception e) {
					exibirDialogoErro("Falha ao deletar contato!");
					e.printStackTrace();
				}
    		}
    	}
    }

    @FXML
    void exibirAbaAtualizacao() {
    	contatoSelecionado = contatos.getSelectionModel().getSelectedItem();
    	
    	if(contatoSelecionado == null) {
    		exibirDialogoErro("Não há nenhum contato selecionado!");
    	} else {
    		atualizar.setDisable(false);
    		abas.getSelectionModel().select(atualizar);
    		nomeContato.setText(contatoSelecionado.getNome());
    		telefoneContato.setText(contatoSelecionado.getTelefone());
    		emailContato.setText(contatoSelecionado.getEmail());
    		enderecoContato.setText(contatoSelecionado.getEndereco());
    		consultarContatos();
    	}
    }
    
    @FXML
    void salvarAtualizacaoContato() {
    	contatoSelecionado.setNome(nomeContato.getText());
    	contatoSelecionado.setTelefone(telefoneContato.getText());
    	contatoSelecionado.setEmail(emailContato.getText());
    	contatoSelecionado.setEndereco(enderecoContato.getText());
    	contatoSelecionado.setUsuario_cpf(Chave.getChave());
    	
    	try {
    		if(verificaDadosNulosAtualizacao() == 0) {
    			dao.atualizar(contatoSelecionado);
        		exibirDialogoInformacao("Contato atualizado com sucesso!");
        		consultarContatos();
        		abas.getSelectionModel().select(consultar);
        		gerenciarAbas();
    		}
		} catch (Exception e) {
			exibirDialogoErro("Erro ao atualizar contato!");
			e.printStackTrace();
		}
    }

    @FXML
    void gerenciarAbas() {
    	if(cadastrar.isSelected() || consultar.isSelected()) {
    		atualizar.setDisable(true);
    		limparCadastroAtualizacaoContato();
    	}
    }

    @FXML
    void limparCadastroNovoContato() {
    	nomeNovoContato.clear();
    	telefoneNovoContato.clear();
    	emailNovoContato.clear();
    	enderecoNovoContato.clear();
    }

    private void limparCadastroAtualizacaoContato() {
    	nomeContato.clear();
    	telefoneContato.clear();
    	emailContato.clear();
    	enderecoContato.clear();
    }

    @FXML
    void salvarNovoContato() {
    	Contato contato = new Contato();
    	
    	contato.setNome(nomeNovoContato.getText());
    	contato.setTelefone(telefoneNovoContato.getText());
    	contato.setEmail(emailNovoContato.getText());
    	contato.setEndereco(enderecoNovoContato.getText());
    	contato.setUsuario_cpf(Chave.getChave());
    	
    	try {
    		if(verificaDadosNulosCadastro() == 0) {
	    		dao.cadastrar(contato);
	    		limparCadastroNovoContato();
	    		exibirDialogoInformacao("Contato cadastrado com sucesso!");
	    		consultarContatos();
    		}
		} catch (Exception e) {
			exibirDialogoErro("Falha ao cadastrar contato!");
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
    
    private boolean exibirDialogoConfimacao(String confirmacao) {
    	Alert alert = new Alert(AlertType.CONFIRMATION);
    	alert.setTitle("Informação");
    	alert.setHeaderText(null);
    	alert.setContentText(confirmacao);
    	
    	Optional<ButtonType> opcao = alert.showAndWait();
    	
    	if(opcao.get() == ButtonType.OK){
    		return true;
    	} else {
    		return false;
    	}
    }
    
    private int verificaDadosNulosCadastro() {
    	if(nomeNovoContato.getText().equals("") || telefoneNovoContato.getText().equals("")) {
    		exibirDialogoErro("Os campos de nome e telefone são obrigatórios!");
    		return 1;
    	} else {
    		return 0;
    	}
    }
    
    private int verificaDadosNulosAtualizacao() {
    	if(nomeContato.getText().equals("") || telefoneContato.getText().equals("")) {
    		exibirDialogoErro("Os campos de nome e telefone são obrigatórios!");
    		return 1;
    	} else {
    		return 0;
    	}
    }
}
