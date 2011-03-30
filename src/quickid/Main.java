package quickid;

import java.awt.event.ActionEvent;
import java.io.File;
import java.io.IOException;
import java.util.ListIterator;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.AbstractAction;
import javax.swing.ActionMap;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.InputMap;
import javax.swing.JComponent;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.event.DocumentEvent;



/**
 *
 * @author Administrator
 */
public class Main extends javax.swing.JFrame
                       implements javax.swing.event.DocumentListener {


    final static String CANCEL_ACTION = "cancel-search";

    private LinkedList<ItemEntry> all = new LinkedList();
    private LinkedList<ItemEntry> found = new LinkedList();
    

    /** Creates new form Main */
    public Main() {
        initComponents();
        try {
            ImageIcon eye = new ImageIcon();
            eye.setImage(ImageIO.read(new File("eye.png")));
        } catch (IOException ex) {}
        ItemList.setCellRenderer(new ItemCellRenderer());

        ItemList.removeAll();

        txtParser tp = new txtParser("items.txt");

        all.addAll(tp.getItems());
        search();
        SearchBox.getDocument().addDocumentListener(this);

        InputMap im = SearchBox.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
        ActionMap am = SearchBox.getActionMap();
        im.put(KeyStroke.getKeyStroke("ESCAPE"), CANCEL_ACTION);
        am.put(CANCEL_ACTION, new CancelAction());
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        scrollPane1 = new java.awt.ScrollPane();
        jScrollPane1 = new javax.swing.JScrollPane();
        ItemList = new javax.swing.JList();
        SearchBox = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("QuickID");
        setMinimumSize(new java.awt.Dimension(150, 200));

        ItemList.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        ItemList.setFixedCellHeight(18);
        jScrollPane1.setViewportView(ItemList);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(SearchBox, javax.swing.GroupLayout.DEFAULT_SIZE, 203, Short.MAX_VALUE)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 203, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(SearchBox, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 388, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        //Schedule a job for the event dispatch thread:
        //creating and showing this application's GUI.
	SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                //Turn off metal's use of bold fonts
                UIManager.put("swing.boldMetal", Boolean.FALSE);
		new Main().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JList ItemList;
    private javax.swing.JTextField SearchBox;
    private javax.swing.JScrollPane jScrollPane1;
    private java.awt.ScrollPane scrollPane1;
    // End of variables declaration//GEN-END:variables

    public void insertUpdate(DocumentEvent e) {
        search();
    }

    public void removeUpdate(DocumentEvent e) {
        search();
    }

    public void changedUpdate(DocumentEvent e) {
    }

    class CancelAction extends AbstractAction {
        public void actionPerformed(ActionEvent ev) {
            SearchBox.setText("");
        }
    }

    public void search() {
        
        ListIterator it = all.listIterator();
        String s = "";
        found.clear();

        if(SearchBox.getText().equals("")) {
            found.addAll(all);
        } else {
            while(it.hasNext()) {
                ItemEntry i = (ItemEntry) it.next();
                s = i.getItemName();
                if(s.toLowerCase().contains(SearchBox.getText().toLowerCase())) {
                    found.add(i);
                }
            }
        }
        ItemList.setListData(found.toArray(new Object[0]));
    }
}