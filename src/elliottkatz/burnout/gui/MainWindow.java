package elliottkatz.burnout.gui;

import java.awt.Color;
import java.awt.Component;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.HashSet;
import java.util.Set;

import javax.swing.DefaultListCellRenderer;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListModel;
import javax.swing.SwingConstants;
import javax.swing.border.MatteBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.plaf.basic.BasicComboBoxRenderer;

import elliottkatz.burnout.dao.ScoreDao;
import elliottkatz.burnout.level.Level;
import elliottkatz.burnout.player.Player;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.ListSelectionModel;

public class MainWindow {

    final JScrollPane scrollPane = new JScrollPane();
    
    private JFrame frmBurnoutScores;
    private ScoreTableModel tableModel = new ScoreTableModel();
    private JTable table = new JTable(tableModel);
    private JLabel lblCurrentPlayer = new JLabel("ROM");
    
    private ScoreDao scoreDao = new ScoreDao();
    private JLabel lblPlayer;
    private final JLabel lblAddScore = new JLabel("Add Score");
    private JLabel lblSaveStatus = new JLabel("");
    private JTextField txtScore;
    private JTextField txtNotes;
    private ScoreTableRenderer scoreTableRenderer = new ScoreTableRenderer(tableModel);

	private JCheckBox chckbxShowRom = new JCheckBox("ROM");
	private final JCheckBox chckbxShowKat = new JCheckBox("KAT");
	private final JLabel lblFilterScores = new JLabel("Filter Scores");
	private final JList<Level> lstLevel = new JList<Level>();

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    MainWindow window = new MainWindow();
                    window.frmBurnoutScores.setVisible(true);
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
        initialize();
    }

    /**
     * Initialize the contents of the frame.
     */
    @SuppressWarnings("unchecked")
    private void initialize() {
        frmBurnoutScores = new JFrame();
        frmBurnoutScores.getContentPane().setBackground(Color.WHITE);
        
        frmBurnoutScores.setTitle("Burnout Scores");
        frmBurnoutScores.setBounds(100, 100, 838, 545);
        frmBurnoutScores.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frmBurnoutScores.getContentPane().setLayout(null);
        
        lblPlayer = new JLabel("Player:");
        lblPlayer.setFont(new Font("Candara", Font.BOLD, 14));
        lblPlayer.setHorizontalAlignment(SwingConstants.CENTER);
        lblPlayer.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
        lblPlayer.setOpaque(true);
        lblPlayer.setBackground(Color.WHITE);
        lblPlayer.setBounds(10, 359, 113, 25);
        frmBurnoutScores.getContentPane().add(lblPlayer);

        lblCurrentPlayer.addMouseListener(new MouseAdapter() {
        	@Override
        	public void mouseClicked(MouseEvent e) {
        		switchPlayer();
        	}
        });
                
        lblCurrentPlayer.setBackground(Color.WHITE);
        lblCurrentPlayer.setOpaque(true);
        lblCurrentPlayer.setHorizontalAlignment(SwingConstants.CENTER);
        lblCurrentPlayer.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
        lblCurrentPlayer.setFont(new Font("Candara", Font.PLAIN, 14));
        
        lblCurrentPlayer.setBounds(10, 383, 113, 25);
        frmBurnoutScores.getContentPane().add(lblCurrentPlayer);
        
        scrollPane.setBackground(Color.WHITE);

        scrollPane.setBounds(171, 24, 630, 299);
        frmBurnoutScores.getContentPane().add(scrollPane);
        table.setRowHeight(20);
        table.setFont(new Font("Candara", Font.PLAIN, 14));
        table.setBackground(new Color(255, 255, 255));
        scrollPane.setViewportView(table);
        lblAddScore.setFont(new Font("Candara", Font.BOLD, 14));
        lblAddScore.setHorizontalAlignment(SwingConstants.CENTER);
        lblAddScore.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
        lblAddScore.setOpaque(true);
        lblAddScore.setBackground(Color.WHITE);
        lblAddScore.setBounds(10, 334, 434, 25);
        
        frmBurnoutScores.getContentPane().add(lblAddScore);
        
        txtScore = new JTextField();
        txtScore.setHorizontalAlignment(SwingConstants.CENTER);
        txtScore.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
        txtScore.setFont(new Font("Candara", Font.PLAIN, 14));
        txtScore.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent event) {
                lblSaveStatus.setText("");
                if(event.getKeyChar() == '\n') {
                    saveScore();
                }
            }
        });
        txtScore.setBounds(121, 383, 86, 25);
        frmBurnoutScores.getContentPane().add(txtScore);
        txtScore.setColumns(10);
        
        JButton btnSave = new JButton("Save");
        btnSave.setFont(new Font("Candara", Font.BOLD, 31));
        btnSave.setBorder(new MatteBorder(3, 3, 3, 3, (Color) new Color(0, 0, 0)));
        btnSave.setBackground(Color.WHITE);
        btnSave.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                if (!txtScore.getText().equals("")) {
                    saveScore();
                }
                else {
                    lblSaveStatus.setForeground(Color.RED);
                    lblSaveStatus.setText("Save failed - You must enter a score");
                }
            }

        });
        btnSave.setBounds(10, 420, 196, 50);
        frmBurnoutScores.getContentPane().add(btnSave);
        
        txtNotes = new JTextField();
        txtNotes.setHorizontalAlignment(SwingConstants.CENTER);
        txtNotes.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
        txtNotes.setFont(new Font("Candara", Font.PLAIN, 14));
        txtNotes.addKeyListener(new KeyAdapter() {
            @Override
            //TODO: this is a copy-paste of the one for the score text box. unify them.
            public void keyTyped(KeyEvent event) {
                lblSaveStatus.setText("");
                if(event.getKeyChar() == '\n') {
                    saveScore();
                }
            }
        });
        txtNotes.setBounds(205, 383, 239, 25);
        frmBurnoutScores.getContentPane().add(txtNotes);
        txtNotes.setColumns(10);
        
        JLabel lblScore = new JLabel("Score:");
        lblScore.setFont(new Font("Candara", Font.BOLD, 14));
        lblScore.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
        lblScore.setHorizontalAlignment(SwingConstants.CENTER);
        lblScore.setOpaque(true);
        lblScore.setBackground(Color.WHITE);
        lblScore.setBounds(120, 359, 86, 25);
        frmBurnoutScores.getContentPane().add(lblScore);
        
        JLabel lblNotes = new JLabel("Notes:");
        lblNotes.setFont(new Font("Candara", Font.BOLD, 14));
        lblNotes.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
        lblNotes.setHorizontalAlignment(SwingConstants.CENTER);
        lblNotes.setOpaque(true);
        lblNotes.setBackground(Color.WHITE);
        lblNotes.setBounds(205, 359, 239, 25);
        frmBurnoutScores.getContentPane().add(lblNotes);
        lblSaveStatus.setHorizontalAlignment(SwingConstants.CENTER);
        lblSaveStatus.setFont(new Font("Candara", Font.PLAIN, 14));
        lblSaveStatus.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
        lblSaveStatus.setBackground(Color.WHITE);
        lblSaveStatus.setOpaque(true);
        
        lblSaveStatus.setBounds(205, 420, 239, 50);
        frmBurnoutScores.getContentPane().add(lblSaveStatus);
        chckbxShowRom.setBorderPainted(true);
        chckbxShowRom.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
        
        chckbxShowRom.setSelected(true);
        chckbxShowRom.setBackground(Color.WHITE);
        chckbxShowRom.setBounds(704, 359, 97, 25);
        frmBurnoutScores.getContentPane().add(chckbxShowRom);
        chckbxShowKat.setBorderPainted(true);
        chckbxShowKat.setSelected(true);
        chckbxShowKat.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
        chckbxShowKat.setBackground(Color.WHITE);
        chckbxShowKat.setBounds(704, 383, 97, 25);
        
        frmBurnoutScores.getContentPane().add(chckbxShowKat);
        lblFilterScores.setOpaque(true);
        lblFilterScores.setHorizontalAlignment(SwingConstants.CENTER);
        lblFilterScores.setFont(new Font("Candara", Font.BOLD, 14));
        lblFilterScores.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
        lblFilterScores.setBackground(Color.WHITE);
        lblFilterScores.setBounds(704, 334, 97, 25);
        
        frmBurnoutScores.getContentPane().add(lblFilterScores);
        
        JScrollPane scrollPane_1 = new JScrollPane();
        scrollPane_1.setBounds(10, 24, 151, 299);
        frmBurnoutScores.getContentPane().add(scrollPane_1);
        scrollPane_1.setViewportView(lstLevel);
        
        lstLevel.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        lstLevel.setOpaque(true);
        lstLevel.setBackground(Color.WHITE);
        
    	DefaultListModel<Level> lstLevelModel = new DefaultListModel<Level>();
        for (Level level : Level.getAllLevels()) {
            lstLevelModel.addElement(level);
        }
        
        lstLevel.setModel(lstLevelModel);
        lstLevel.setSelectedValue(Level.A_BRIDGE_TOO_FAR, true);
        
        lstLevel.setCellRenderer(new DefaultListCellRenderer() {
			private static final long serialVersionUID = 1L;

			@Override
        	public Component getListCellRendererComponent(JList<?> list,
        			Object value, int index, boolean isSelected,
        			boolean cellHasFocus) {
                Component component = super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                setText(((Level) value).getName());
                return component;
        	}
        });
        
        lstLevel.addListSelectionListener(new TableUpdatingListener());
        
        JLabel lblImage = new JLabel("");
        lblImage.setIcon(new ImageIcon(MainWindow.class.getResource("/elliottkatz/burnout/gui/burnout.jpg")));
        lblImage.setBounds(0, 0, 822, 507);
        frmBurnoutScores.getContentPane().add(lblImage);
                
        chckbxShowRom.addItemListener(new TableUpdatingListener());
        chckbxShowKat.addItemListener(new TableUpdatingListener());
        
        table.setDefaultRenderer(Object.class, scoreTableRenderer);
        
        populateTable();
    }
    
    private void populateTable() {
    	Set<Player> playersToShow = new HashSet<Player>();
    	if(chckbxShowRom.isSelected()) {
			playersToShow.add(Player.ROM);
		}
    	if(chckbxShowKat.isSelected()) {
			playersToShow.add(Player.KAT);
		}
		tableModel.setScores(scoreDao.getScoresByLevelAndPlayers(
				(Level) lstLevel.getSelectedValue(), playersToShow));
		scoreTableRenderer.computeMins();
		tableModel.fireTableDataChanged();
		scrollToBottom();
    }

	private void scrollToBottom() {
		scrollPane.validate();
		JScrollBar vertical = scrollPane.getVerticalScrollBar();
		vertical.setValue(vertical.getMaximum());
	}
    
    private void saveScore() {
    	boolean fail = false;
    	String error = "";
        try {
			scoreDao.createScore((Player.getByName(lblCurrentPlayer.getText())), (Level)lstLevel.getSelectedValue(), Float.parseFloat(txtScore.getText()), txtNotes.getText());
		} catch (Exception e) {
			fail = true;
			error = e.getMessage();
		}
        populateTable();
		if (!fail) {
			lblSaveStatus.setForeground(Color.BLACK);
			lblSaveStatus.setText("Saved");
			txtScore.setText("");
			txtNotes.setText("");
			switchPlayer();
			txtScore.requestFocus();
		}
		else {
			lblSaveStatus.setForeground(Color.RED);
			lblSaveStatus.setText("Save failed - " + error);
		}
    }

	private void switchPlayer() {
		Player otherPlayer = Player.getOtherPlayer(lblCurrentPlayer.getText());
		lblCurrentPlayer
				.setText(otherPlayer.toString());
	}
    
	private class TableUpdatingListener implements ItemListener, ListSelectionListener {

		@Override
		public void itemStateChanged(ItemEvent e) {
			populateTable();
		}

		@Override
		public void valueChanged(ListSelectionEvent e) {
			populateTable();
		}

	}
}