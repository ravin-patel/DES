import java.awt.EventQueue;
import javax.crypto.Cipher;
import javax.crypto.CipherOutputStream;
import javax.crypto.spec.SecretKeySpec;
import javax.swing.JFrame;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.awt.Font;
import java.awt.Color;


public class Img {

	private JFrame frame;
	private JTextField filePath;
	private JButton encrypt;
	private JButton decrypt;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() 
		{
			public void run() 
			{
				try 
				{
					Img window = new Img();
					window.frame.setVisible(true);
				} 
				catch (Exception e)
				{
					e.printStackTrace();
				}
			}
		});
	}
	/**
	 * Create the application.
	 */
	public Img() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() 
	{
		/*
		 * Makes and sets the bounds of the frame
		 */
		frame = new JFrame();
		frame.getContentPane().setBackground(new Color(176, 196, 222));
		frame.getContentPane().setForeground(Color.BLACK);
		frame.setBounds(100, 100, 432, 128);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		/*
		 * Makes and edits text box settings
		 */
		filePath = new JTextField();
		filePath.setBounds(6, 6, 270, 28);
		frame.getContentPane().add(filePath);
		filePath.setColumns(10);
		/*
		 * Makes and edits buttons settings
		 */
		JButton chooseButton = new JButton("Choose");
		chooseButton.setFont(new Font("LiHei Pro", Font.BOLD, 16));
		chooseButton.setBounds(283, 6, 117, 29);
		frame.getContentPane().add(chooseButton);
		/*
		 * This segment of code will let us choose a file when the button is pressed
		 * and then the path of the file will show up in the text box, you can also type the path in
		 * if you choose to do so
		 */
		chooseButton.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				JFileChooser chooser = new JFileChooser();
				chooser.showOpenDialog(null);
				File file = chooser.getSelectedFile();
				filePath.setText(file.getAbsolutePath());
			}
		});
		/*
		 * The encrypt button will get the filepath from the textbox(which was chosen through JFileChooser)
		 * It will then get a 8bit (# of bits in DES) code and use it as the secret key for the encryption
		 * Then the cipher will encrpyt to a output file and name is Encrypted.jpg (Path defaulted to workspace) 
		 */
		encrypt = new JButton("Encrypt");
		encrypt.setFont(new Font("LiHei Pro", Font.BOLD, 16));
		encrypt.setBounds(6, 48, 191, 29);
		frame.getContentPane().add(encrypt);
		encrypt.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				try{
					FileInputStream f = new FileInputStream(filePath.getText());
					FileOutputStream outputfile = new FileOutputStream("Encrypted.jpg");	
					byte key[]="CsMTH816".getBytes(); //key has to be 8bits for DES
					SecretKeySpec secretKey = new SecretKeySpec(key,"DES");
					Cipher encryption = Cipher.getInstance("DES");
					encryption.init(Cipher.ENCRYPT_MODE, secretKey);
					CipherOutputStream c = new CipherOutputStream(outputfile,encryption);
					byte[] buffer = new byte[1024];
					int read;
					while((read = f.read(buffer)) != -1){
						c.write(buffer, 0, read);
					}
					f.close();
					outputfile.flush();
					c.close();
					JOptionPane.showMessageDialog(null,"The Picture has been encrypted");
					
				}
				catch(Exception ex){
					JOptionPane.showMessageDialog(null,ex);
				}
			}
		});
		/*
		 * The decrpytion process is almost the exact same, infact its almost identical
		 * The only difference is that the operation mode on the initialize method is set to DECRYPT_MODE
		 */
		decrypt = new JButton("Decrypt");
		decrypt.setFont(new Font("LiHei Pro", Font.BOLD, 16));
		decrypt.setBounds(209, 48, 191, 29);
		frame.getContentPane().add(decrypt);
		decrypt.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				try{
					FileInputStream f = new FileInputStream(filePath.getText());
					FileOutputStream outputfile = new FileOutputStream("Decrypted.jpg");	
					byte key[]="CsMTH816".getBytes(); //key has to be 8bits for DES
					SecretKeySpec secretKey = new SecretKeySpec(key,"DES");
					Cipher decryption = Cipher.getInstance("DES");
					decryption.init(Cipher.DECRYPT_MODE, secretKey);
					CipherOutputStream c = new CipherOutputStream(outputfile,decryption);
					byte[] buffer = new byte[1024];
					int read;
					while((read = f.read(buffer)) != -1)
					{
						c.write(buffer, 0, read);
					}
					f.close();
					outputfile.flush();
					c.close();
					JOptionPane.showMessageDialog(null,"The Picture has been decrypted");
					
				}
				catch(Exception ex){
					JOptionPane.showMessageDialog(null,ex);
				}
			}
		});

	}
}
