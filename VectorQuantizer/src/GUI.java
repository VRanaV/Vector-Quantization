import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextField;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.awt.event.ActionEvent;
import java.awt.Color;

public class GUI extends JFrame {

	private JPanel contentPane;
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_2;
	private JTextField textField_3;

	/**
	 * Launch the application.
	 */
	int heightt;
	int widthh;
	int CBSs;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GUI frame = new GUI();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public GUI() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		textField = new JTextField();
		textField.setBounds(75, 30, 54, 20);
		contentPane.add(textField);
		textField.setColumns(10);

		textField_1 = new JTextField();
		textField_1.setBounds(75, 77, 54, 20);
		contentPane.add(textField_1);
		textField_1.setColumns(10);

		textField_2 = new JTextField();
		textField_2.setBounds(75, 123, 54, 20);
		contentPane.add(textField_2);
		textField_2.setColumns(10);

		textField_3 = new JTextField();
		textField_3.setBounds(75, 177, 125, 32);
		contentPane.add(textField_3);
		textField_3.setColumns(10);

		JLabel lblHeight = new JLabel("height");
		lblHeight.setFont(new Font("Times New Roman", Font.PLAIN, 14));
		lblHeight.setBounds(23, 32, 55, 17);
		contentPane.add(lblHeight);

		JLabel lblWidth = new JLabel("width");
		lblWidth.setFont(new Font("Times New Roman", Font.PLAIN, 14));
		lblWidth.setBounds(23, 79, 46, 14);
		contentPane.add(lblWidth);

		JLabel lblCbs = new JLabel("CBS");
		lblCbs.setFont(new Font("Times New Roman", Font.PLAIN, 14));
		lblCbs.setBounds(23, 125, 46, 14);
		contentPane.add(lblCbs);

		JLabel lblPath = new JLabel("Path");
		lblPath.setFont(new Font("Times New Roman", Font.PLAIN, 14));
		lblPath.setBounds(23, 185, 46, 14);
		contentPane.add(lblPath);

		JButton Quantize = new JButton("Quantize");
		Quantize.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				String height;
				height = textField.getText();
				heightt = Integer.parseInt(height);
				heightt=2;
				///////////////////////////////////////////////////////////
				String width;
				width = textField_1.getText();
				widthh = Integer.parseInt(width);
                widthh=2;
				///////////////////////////////////////////////////////////
				String CBS;
				CBS = textField_2.getText();
				CBSs = Integer.parseInt(CBS);
				// System.out.println(height);
				///////////////////////////////////////////////////////////
				String path;
				path = textField_3.getText();
				System.out.println(path);
				try {

					VectorQuantizer.Compression(heightt, widthh, CBSs, path); 
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

			}
		});

		Quantize.setForeground(new Color(128, 0, 128));
		Quantize.setFont(new Font("Times New Roman", Font.BOLD, 14));
		Quantize.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {

			}
		});

		Quantize.setBounds(266, 47, 105, 23);
		contentPane.add(Quantize);

		JButton btnDequantize = new JButton("DeQuantize");

		btnDequantize.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				try {
					VectorQuantizer.Decompression(heightt, widthh, CBSs);
				} catch (IOException | ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});

		btnDequantize.setFont(new Font("Times New Roman", Font.BOLD, 14));
		btnDequantize.setForeground(new Color(128, 0, 128));
		btnDequantize.setBounds(266, 107, 105, 23);
		contentPane.add(btnDequantize);
	}
}
