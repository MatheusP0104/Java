import javax.swing.*;
import java.io.*;
import java.util.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class CadastroProdutos extends JFrame {
    private JLabel lbCodProduto, lbNomeProduto, lbQuantProduto, lbDataValid;
    private JTextField tfCod, tfNome, tfQuant, tfData;
    private JButton btInserir, btListarProd;
    private File pasta = new File("./meusprodutos"); 
    private File arquivo = new File(pasta, "produtos.txt"); 

    public static void main(String[] args){
        new CadastroProdutos();
    }

    public CadastroProdutos(){
        setTitle("Cadastro de Produtos");
        setSize(550, 230);
        setLayout(null);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        lbCodProduto = new JLabel("Código do Produto:");
        lbNomeProduto = new JLabel("Nome do Produto:");
        lbQuantProduto = new JLabel("Quantidade no Estoque:");
        lbDataValid = new JLabel("Data de Validade:");
        tfCod = new JTextField();
        tfNome = new JTextField();
        tfQuant = new JTextField();
        tfData = new JTextField();
        btInserir = new JButton("Inserir Produto");
        btListarProd = new JButton("Listar Produto");

        lbCodProduto.setBounds(10, 10, 150, 20);
        tfCod.setBounds(120, 10, 50, 20);
        lbNomeProduto.setBounds(10, 40, 150, 20);
        tfNome.setBounds(120, 40, 300, 20);
        lbQuantProduto.setBounds(10, 70, 150, 20);
        tfQuant.setBounds(150, 70, 50, 20);
        lbDataValid.setBounds(10, 100, 150, 20);
        tfData.setBounds(120, 100, 70, 20);
        btInserir.setBounds(10, 150, 150, 30);
        btListarProd.setBounds(180, 150, 150, 30);


        add(lbCodProduto);
        add(tfCod);
        add(lbNomeProduto);
        add(tfNome);
        add(lbQuantProduto);
        add(tfQuant);
        add(lbDataValid);
        add(tfData);
        add(btInserir);
        add(btListarProd);
        
        btInserir.addActionListener(e -> inserirProduto());
        btListarProd.addActionListener(e -> ListarProdutos());

        setVisible(true);
    }

    private List<String[]> carregarProdutos(){
        List<String[]> produtos = new ArrayList<>();

        if(arquivo.exists()){
            try(BufferedReader reader = new BufferedReader(new FileReader(arquivo))) {
                String linha;
                while ((linha = reader.readLine()) != null) {
                    String[] partes = linha.split(";");
                    if(partes.length == 4) {
                        produtos.add(new String[]{partes[0].trim(), partes[1].trim(), partes[2].trim(), partes[3].trim()});
                    }
                }
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(this,"Erro ao ler o arquivo: " + ex.getMessage());
            }
        }
        return produtos;
    }

    private void inserirProduto(){
        String cod = tfCod.getText().trim();
        String nome = tfNome.getText().trim();
        String quantidade = tfQuant.getText().trim();
        String data = tfData.getText().trim();

        if(cod.isEmpty() || nome.isEmpty() || quantidade.isEmpty() || data.isEmpty()){
            JOptionPane.showMessageDialog(this,"Preencha todos os campos!");
        }

        int quantidadeEstoque;
        try{
            quantidadeEstoque = Integer.parseInt(quantidade);
            if(quantidadeEstoque < 0) throw new NumberFormatException();
        } catch(NumberFormatException ex){
            JOptionPane.showMessageDialog(this, "Quantidade no estoque não pode ser negativa");
            return;
        }

        LocalDate dataValidade;
        try{
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            dataValidade = LocalDate.parse(data, formatter);
            if(dataValidade.isBefore(LocalDate.now())) {
                JOptionPane.showMessageDialog(this,"A data de validade deve ser futura.");
                return;
            } 
        } catch (DateTimeParseException ex){
                JOptionPane.showMessageDialog(this,"Data de validade inválida.use o formato dd/mm/yyyy");
                return;
            }

        if(!pasta.exists()){
            pasta.mkdirs();
        }

        List <String[]> produtos  = carregarProdutos();
            for(String[] produto : produtos){
                if(produto[1].equalsIgnoreCase(nome)){
                    JOptionPane.showMessageDialog(this,"Esse nome ja está cadastrado.");
                    return;
                }
            }

            for(String[] produto : produtos){
                if(produto[0].equalsIgnoreCase(cod)){
                    JOptionPane.showMessageDialog(this,"Esse Código ja está cadastrado.");
                    return;
                }
            }
        
         try (BufferedWriter writer = new BufferedWriter(new FileWriter(arquivo, true))) {
            writer.write(cod + ";" + nome + ";" + quantidade + ";" + data);
            writer.newLine();
            JOptionPane.showMessageDialog(this, "Produto inserido com sucesso!");
            tfCod.setText("");
            tfNome.setText("");
            tfQuant.setText("");
            tfData.setText("");
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this, "Erro ao escrever no arquivo: " + ex.getMessage());
        }
    }

    private void ListarProdutos(){
        List<String[]> produtos = carregarProdutos();
        if(produtos.isEmpty()){
            JOptionPane.showMessageDialog(this,"Nenhum Produto Cadastrado.");
        } else {
            String mensagem = "Produtos Cadastrados:\n";
            for(String[] produto : produtos){
                mensagem += "Código: " + produto[0] +"\n"+ "Nome: " + produto[1] +"\n"+ "Estoque: " + produto[2] + " unidades" +"\n"+ "Data de validade: " + produto[3] +"\n"+ "==============================="+"\n"; 
            }
            JTextArea textArea = new JTextArea(mensagem.toString());
            textArea.setEditable(false);
            JScrollPane scrollPane = new JScrollPane(textArea);
            scrollPane.setPreferredSize(new java.awt.Dimension(350, 250));

            JOptionPane.showMessageDialog(this, scrollPane, "Lista de Produtos", JOptionPane.INFORMATION_MESSAGE);
        }
    }

}


