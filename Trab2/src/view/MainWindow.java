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
import java.awt.FlowLayout;

public class MainWindow implements ActionListener {

	private JFrame frame;
	private JTextArea taResult;
	private JFormattedTextField tfInput;
	private JLabel lblResult;
	private JButton btnCalculateOne;
	private JButton btnCalculateAll;
	
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
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
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
		
		JPanel northPanel = new JPanel();
		frame.getContentPane().add(northPanel, BorderLayout.NORTH);
		northPanel.setLayout(new BoxLayout(northPanel, BoxLayout.Y_AXIS));
		
		JPanel panel1 = new JPanel();
		northPanel.add(panel1);
		panel1.setLayout(new BoxLayout(panel1, BoxLayout.X_AXIS));
		
		Component horizontalStrut = Box.createHorizontalStrut(5);
		panel1.add(horizontalStrut);
		
		JLabel lblInput = new JLabel("Input:");
		panel1.add(lblInput);
		
		Component horizontalStrut_2 = Box.createHorizontalStrut(5);
		panel1.add(horizontalStrut_2);
		
		tfInput = new JFormattedTextField();
		panel1.add(tfInput);
		
		Component horizontalStrut_1 = Box.createHorizontalStrut(5);
		panel1.add(horizontalStrut_1);
		
		JPanel panel2 = new JPanel();
		northPanel.add(panel2);
		panel2.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		btnCalculateOne = new JButton("Calculate One");
		btnCalculateOne.addActionListener(this);
		panel2.add(btnCalculateOne);
		
		btnCalculateAll = new JButton("Calculate All");
		btnCalculateAll.addActionListener(this);
		panel2.add(btnCalculateAll);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String input = tfInput.getText();
		if(!isValidInput(input)){
			showError("This isn't a positive integer.");
			return;
		}
		
		// Trava a interface
		lblResult.setText("Calculating...");
		taResult.setText("");
		tfInput.setEnabled(false);
		btnCalculateOne.setEnabled(false);
		btnCalculateAll.setEnabled(false);
		
		BigInteger p = new BigInteger(input);
		pr.calculate(p, (e.getActionCommand() == "Calculate All"));
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
		
		// Destrava a interface
		tfInput.setEnabled(true);
		btnCalculateOne.setEnabled(true);
		btnCalculateAll.setEnabled(true);
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
		
		//Destrava a interface
		tfInput.setEnabled(true);
		btnCalculateOne.setEnabled(true);
		btnCalculateAll.setEnabled(true);
		
		JOptionPane.showMessageDialog(frame, msg, "Error", JOptionPane.ERROR_MESSAGE);
	}

}
