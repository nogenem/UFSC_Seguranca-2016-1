package view;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigInteger;
import java.util.Set;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import model.PrimitiveRoot;

public class MainWindow implements ActionListener {

	private JFrame frame;
	private JTextArea taResult;
	private JFormattedTextField tfInput;
	private JLabel lblResult;
	private JButton btnCalculate;
	
	private PrimitiveRoot pr;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainWindow window = new MainWindow();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public MainWindow() {
		this.pr = new PrimitiveRoot(this);
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setTitle("Primitive Root Modulo n");
		BorderLayout borderLayout = (BorderLayout) frame.getContentPane().getLayout();
		borderLayout.setVgap(5);
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JPanel northPanel = new JPanel();
		frame.getContentPane().add(northPanel, BorderLayout.NORTH);
		northPanel.setLayout(new BoxLayout(northPanel, BoxLayout.X_AXIS));
		
		Component horizontalStrut = Box.createHorizontalStrut(5);
		northPanel.add(horizontalStrut);
		
		JLabel lblInput = new JLabel("Input:");
		northPanel.add(lblInput);
		
		Component horizontalStrut_2 = Box.createHorizontalStrut(5);
		northPanel.add(horizontalStrut_2);
		
		tfInput = new JFormattedTextField();
		northPanel.add(tfInput);
		
		Component horizontalStrut_3 = Box.createHorizontalStrut(5);
		northPanel.add(horizontalStrut_3);
		
		btnCalculate = new JButton("Calculate");
		btnCalculate.addActionListener(this);
		northPanel.add(btnCalculate);
		
		Component horizontalStrut_1 = Box.createHorizontalStrut(5);
		northPanel.add(horizontalStrut_1);
		
		JPanel southPanel = new JPanel();
		frame.getContentPane().add(southPanel, BorderLayout.CENTER);
		southPanel.setLayout(new BorderLayout(5, 0));
		
		Box horizontalBox = Box.createHorizontalBox();
		southPanel.add(horizontalBox, BorderLayout.NORTH);
		
		Component horizontalStrut_6 = Box.createHorizontalStrut(5);
		horizontalBox.add(horizontalStrut_6);
		
		lblResult = new JLabel("Result:");
		horizontalBox.add(lblResult);
		
		JScrollPane scrollPane = new JScrollPane();
		southPanel.add(scrollPane, BorderLayout.CENTER);
		
		taResult = new JTextArea();
		taResult.setEditable(false);
		scrollPane.setViewportView(taResult);
		
		Component horizontalStrut_4 = Box.createHorizontalStrut(1);
		southPanel.add(horizontalStrut_4, BorderLayout.WEST);
		
		Component horizontalStrut_5 = Box.createHorizontalStrut(1);
		southPanel.add(horizontalStrut_5, BorderLayout.EAST);
		
		Component verticalStrut = Box.createVerticalStrut(7);
		southPanel.add(verticalStrut, BorderLayout.SOUTH);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		lblResult.setText("Calculating...");
		taResult.setText("");
		tfInput.setEnabled(false);
		btnCalculate.setEnabled(false);
		
		String input = tfInput.getText();
		if(!isValidInput(input)){
			showError("This isn't a positive integer.");
			return;
		}
		
		BigInteger p = new BigInteger(input);
		pr.calculate(p);
	}
	
	/**
	 * Verifica se o input é um inteiro positivo.
	 * 
	 * @param input		Input para ser verificado.
	 * @return			TRUE se o input for um inteiro positivo,
	 * 			   </br>FALSE caso contrario.
	 */
	private boolean isValidInput(String input){
		return input.matches("\\d+");//positive integer
	}
	
	/**
	 * Função chamada pela classe PrimitiveRoot
	 *  após calcular as raízes primitivas de um 
	 *  numero previamente passado a ela.
	 * 
	 * @param result	As raízes primitivas.
	 * @param time		O tempo, em segundos, que levou para 
	 * 					 realizar o calculo.
	 */
	public void setResult(Set<BigInteger> result, double time){
		lblResult.setText(String.format("Result (%.2fs):", time));
		
		String txt = result.toString();
		txt = txt.substring(1, txt.length()-1);
		txt = txt.replaceAll(", ", ",\n");
		taResult.setText(txt);
		
		tfInput.setEnabled(true);
		btnCalculate.setEnabled(true);
	}
	
	/**
	 * Função chamada pela classe PrimitiveRoot
	 *  caso ocorra um erro durante o calculo
	 *  das raízes primitivas.
	 * 
	 * @param msg	Mensagem de erro.
	 */
	public void showError(String msg){
		lblResult.setText("Result:");
		JOptionPane.showMessageDialog(frame, msg, "Error", JOptionPane.ERROR_MESSAGE);
	}

}
