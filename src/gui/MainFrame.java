package gui;

import model.*;
import service.AppService;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.LocalDate;
import java.util.ArrayList;

public class MainFrame extends JFrame {
    // Fixed black, white and pink theme (independent of the operating-system theme).
    private static final Color NAVY = new Color(28, 25, 29);       // near-black
    private static final Color BLUE = new Color(220, 82, 139);     // pink accent
    private static final Color BG = new Color(250, 246, 248);      // soft pink-white
    private static final Color WHITE = new Color(255, 255, 255);
    private static final Color TEXT = new Color(38, 34, 38);
    private static final Color MUTED = new Color(112, 101, 108);
    private static final Color BORDER = new Color(232, 215, 224);
    private static final Color PALE_PINK = new Color(255, 235, 244);
    private final AppService service = new AppService();
    private final CardLayout cards = new CardLayout();
    private final JPanel root = new JPanel(cards);
    private User currentUser;
    private JPanel appShell;

    public MainFrame() {
        applyFixedTheme();
        setTitle("Campus Lost & Found Management System");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(1120, 720);
        setMinimumSize(new Dimension(900, 600));
        setLocationRelativeTo(null);
        root.add(buildLogin(), "login");
        root.add(buildRegister(), "register");
        add(root);
        cards.show(root, "login");
    }

    private void applyFixedTheme() {
        // Use a predictable Swing look and feel instead of inheriting Linux/Windows dark mode.
        try { UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName()); }
        catch (Exception ignored) { }
        UIManager.put("Panel.background", BG);
        UIManager.put("Label.foreground", TEXT);
        UIManager.put("OptionPane.background", WHITE);
        UIManager.put("OptionPane.messageForeground", TEXT);
        UIManager.put("TextField.background", WHITE);
        UIManager.put("TextField.foreground", TEXT);
        UIManager.put("PasswordField.background", WHITE);
        UIManager.put("PasswordField.foreground", TEXT);
        UIManager.put("TextArea.background", WHITE);
        UIManager.put("TextArea.foreground", TEXT);
        UIManager.put("ComboBox.background", WHITE);
        UIManager.put("ComboBox.foreground", TEXT);
        UIManager.put("Table.background", WHITE);
        UIManager.put("Table.foreground", TEXT);
        UIManager.put("Table.selectionBackground", PALE_PINK);
        UIManager.put("Table.selectionForeground", TEXT);
        UIManager.put("TableHeader.background", PALE_PINK);
        UIManager.put("TableHeader.foreground", TEXT);
        UIManager.put("ScrollPane.background", WHITE);
        UIManager.put("Viewport.background", WHITE);
    }

    private JPanel page() { JPanel p = new JPanel(new BorderLayout(18, 18)); p.setBackground(BG); p.setBorder(new EmptyBorder(24, 28, 24, 28)); return p; }
    private JLabel heading(String text) { JLabel l = new JLabel(text); l.setFont(new Font("SansSerif", Font.BOLD, 25)); l.setForeground(NAVY); return l; }
    private JButton button(String text) {
        JButton b = new JButton(text); b.setFocusPainted(false); b.setFont(new Font("SansSerif", Font.BOLD, 13));
        b.setForeground(WHITE); b.setBackground(BLUE); b.setBorder(new EmptyBorder(11, 16, 11, 16)); b.setOpaque(true); b.setBorderPainted(false); b.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR)); return b;
    }
    private JTextField field(int columns) { JTextField f = new JTextField(columns); f.setFont(new Font("SansSerif", Font.PLAIN, 14)); f.setForeground(TEXT); f.setBackground(WHITE); f.setCaretColor(BLUE); f.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(BORDER), new EmptyBorder(9,10,9,10))); return f; }
    private void showMessage(String message) { JOptionPane.showMessageDialog(this, message); }
    private void styleCombo(JComboBox<?> combo) {
        combo.setBackground(WHITE);
        combo.setForeground(TEXT);
        combo.setFont(new Font("SansSerif", Font.PLAIN, 13));
    }

    private JPanel buildLogin() {
        JPanel outer = new JPanel(new GridBagLayout()); outer.setBackground(BG);
        JPanel box = new JPanel(new GridBagLayout()); box.setBackground(WHITE); box.setBorder(new EmptyBorder(35, 45, 35, 45));
        GridBagConstraints c = new GridBagConstraints(); c.gridx=0; c.gridy=0; c.fill=GridBagConstraints.HORIZONTAL; c.weightx=1; c.insets=new Insets(7,0,7,0);
        JLabel title = new JLabel("Campus Lost & Found"); title.setFont(new Font("SansSerif",Font.BOLD,27)); title.setForeground(NAVY); box.add(title,c);
        c.gridy++; JLabel sub = new JLabel("Sign in to your campus account"); sub.setForeground(Color.GRAY); box.add(sub,c);
        c.gridy++; box.add(new JLabel("Email address"),c); c.gridy++; JTextField email=field(25); box.add(email,c);
        c.gridy++; box.add(new JLabel("Password"),c); c.gridy++; JPasswordField pass=new JPasswordField(25); pass.setBorder(field(1).getBorder()); box.add(pass,c);
        c.gridy++; JButton login=button("Login"); box.add(login,c);
        c.gridy++; JButton register=new JButton("Create a student account"); register.setBorderPainted(false); register.setContentAreaFilled(false); register.setForeground(BLUE); box.add(register,c);
        c.gridy++; JLabel hint=new JLabel("Admin demo: admin@campus.com  |  admin123"); hint.setFont(new Font("SansSerif",Font.PLAIN,11)); hint.setForeground(Color.GRAY); box.add(hint,c);
        login.addActionListener(e -> {
            User user=service.login(email.getText(),new String(pass.getPassword()));
            if(user==null) showMessage("Invalid email or password."); else { currentUser=user; showDashboard(); }
        });
        pass.addActionListener(e -> login.doClick());
        register.addActionListener(e -> cards.show(root,"register"));
        outer.add(box); return outer;
    }

    private JPanel buildRegister() {
        JPanel outer=new JPanel(new GridBagLayout()); outer.setBackground(BG);
        JPanel box=new JPanel(new GridBagLayout()); box.setBackground(WHITE); box.setBorder(new EmptyBorder(25,40,25,40));
        GridBagConstraints c=new GridBagConstraints(); c.gridx=0;c.gridy=0;c.fill=GridBagConstraints.HORIZONTAL;c.weightx=1;c.insets=new Insets(5,0,5,0);
        JLabel title=new JLabel("Student Registration");title.setFont(new Font("SansSerif",Font.BOLD,25));title.setForeground(NAVY);box.add(title,c);
        JTextField name=field(25), sid=field(25), dept=field(25), email=field(25); JPasswordField pass=new JPasswordField(25); pass.setBorder(field(1).getBorder());
        String[] labels={"Full name","Student ID","Department","Email","Password"}; JComponent[] inputs={name,sid,dept,email,pass};
        for(int i=0;i<labels.length;i++){c.gridy++;box.add(new JLabel(labels[i]),c);c.gridy++;box.add(inputs[i],c);}
        c.gridy++; JButton create=button("Create account");box.add(create,c);
        c.gridy++; JButton back=new JButton("Back to login");back.setBorderPainted(false);back.setContentAreaFilled(false);back.setForeground(BLUE);box.add(back,c);
        create.addActionListener(e->{String error=service.register(name.getText(),sid.getText(),dept.getText(),email.getText(),new String(pass.getPassword()));if(error!=null)showMessage(error);else{showMessage("Registration successful. Please log in.");cards.show(root,"login");}});
        back.addActionListener(e->cards.show(root,"login")); outer.add(box);return outer;
    }

    private void showDashboard() {
        JPanel shell=new JPanel(new BorderLayout());
        JPanel sidebar=new JPanel();sidebar.setBackground(NAVY);sidebar.setPreferredSize(new Dimension(220,0));sidebar.setLayout(new BoxLayout(sidebar,BoxLayout.Y_AXIS));sidebar.setBorder(new EmptyBorder(22,14,18,14));
        JLabel brand=new JLabel("CAMPUS  L&F");brand.setForeground(Color.WHITE);brand.setFont(new Font("SansSerif",Font.BOLD,19));brand.setBorder(new EmptyBorder(0,8,25,0));sidebar.add(brand);
        JPanel content=new JPanel(cards);content.setBackground(BG);
        content.add(buildDashboardPage(),"home");
        if(currentUser instanceof Student student){
            content.add(buildReportPage(true,student),"lost");content.add(buildReportPage(false,student),"found");
            content.add(buildBrowsePage(student),"browse");content.add(buildMyReportsPage(student),"reports");content.add(buildMyClaimsPage(student),"myclaims");
        } else {
            content.add(buildAdminItemsPage(),"items");content.add(buildAdminClaimsPage(),"claims");
        }
        addNav(sidebar,"Dashboard","home",content);
        if(currentUser instanceof Student){addNav(sidebar,"Report Lost Item","lost",content);addNav(sidebar,"Report Found Item","found",content);addNav(sidebar,"Browse Items","browse",content);addNav(sidebar,"My Reports","reports",content);addNav(sidebar,"My Claims","myclaims",content);}
        else {addNav(sidebar,"Manage Items","items",content);addNav(sidebar,"Claim Requests","claims",content);}
        sidebar.add(Box.createVerticalGlue()); JButton logout=new JButton("Logout");logout.setAlignmentX(Component.LEFT_ALIGNMENT);logout.setForeground(WHITE);logout.setBackground(new Color(174, 54, 91));logout.setOpaque(true);logout.setBorderPainted(false);logout.setFocusPainted(false);logout.setMaximumSize(new Dimension(190,42));logout.addActionListener(e->{currentUser=null;appShell=null;root.removeAll();root.add(buildLogin(),"login");root.add(buildRegister(),"register");cards.show(root,"login");});sidebar.add(logout);
        shell.add(sidebar,BorderLayout.WEST);shell.add(content,BorderLayout.CENTER);if(appShell!=null)root.remove(appShell);appShell=shell;root.add(appShell,"app");cards.show(root,"app");
    }
    private void addNav(JPanel sidebar,String label,String key,JPanel content){JButton b=new JButton(label);b.setHorizontalAlignment(SwingConstants.LEFT);b.setMaximumSize(new Dimension(195,42));b.setForeground(WHITE);b.setBackground(NAVY);b.setOpaque(true);b.setBorderPainted(false);b.setBorder(new EmptyBorder(10,12,10,8));b.setFocusPainted(false);b.setAlignmentX(Component.LEFT_ALIGNMENT);b.addActionListener(e->cards.show(content,key));sidebar.add(b);sidebar.add(Box.createVerticalStrut(5));}

    private JPanel buildDashboardPage(){
        JPanel p=page(); JPanel top=new JPanel(new BorderLayout());top.setOpaque(false);top.add(heading("Dashboard"),BorderLayout.WEST);
        JLabel welcome=new JLabel("Welcome, "+currentUser.getName());welcome.setForeground(Color.GRAY);top.add(welcome,BorderLayout.EAST);p.add(top,BorderLayout.NORTH);
        JPanel center=new JPanel(new GridLayout(2,2,16,16));center.setOpaque(false);
        long lost=service.getItems().stream().filter(i->i instanceof LostItem && i.getStatus().equals("Lost")).count();
        long found=service.getItems().stream().filter(i->i instanceof FoundItem && !i.getStatus().equals("Returned")).count();
        long pending=service.getClaims().stream().filter(c->c.getStatus().equals("Pending")).count();
        center.add(statCard("Total Items",String.valueOf(service.getItems().size()),"All reports"));center.add(statCard("Lost Items",String.valueOf(lost),"Waiting to be found"));center.add(statCard("Found Items",String.valueOf(found),"Reported found items"));center.add(statCard("Pending Claims",String.valueOf(pending),"Awaiting admin review"));
        p.add(center,BorderLayout.CENTER);
        JTextArea note=new JTextArea("How it works\n\n1. Students report lost or found belongings.\n2. Students browse found items and submit a claim.\n3. Admin reviews claim requests.\n4. After handover, the admin marks the item as returned.\n\nYour data is saved locally on this computer.");note.setEditable(false);note.setFont(new Font("SansSerif",Font.PLAIN,14));note.setBackground(WHITE);note.setForeground(TEXT);note.setBorder(new EmptyBorder(18,18,18,18));p.add(note,BorderLayout.SOUTH);return p;
    }
    private JPanel statCard(String title,String value,String desc){JPanel card=new JPanel(new GridLayout(3,1,4,4));card.setBackground(WHITE);card.setBorder(new EmptyBorder(18,20,18,20));JLabel t=new JLabel(title);t.setForeground(Color.GRAY);JLabel v=new JLabel(value);v.setFont(new Font("SansSerif",Font.BOLD,32));v.setForeground(NAVY);JLabel d=new JLabel(desc);d.setForeground(Color.GRAY);card.add(t);card.add(v);card.add(d);return card;}

    private JPanel buildReportPage(boolean lost,Student student){
        JPanel p=page();p.add(heading(lost?"Report a Lost Item":"Report a Found Item"),BorderLayout.NORTH);
        JPanel form=new JPanel(new GridBagLayout());form.setBackground(WHITE);form.setBorder(new EmptyBorder(22,25,22,25));GridBagConstraints c=new GridBagConstraints();c.gridx=0;c.gridy=0;c.anchor=GridBagConstraints.WEST;c.fill=GridBagConstraints.HORIZONTAL;c.weightx=1;c.insets=new Insets(7,7,7,7);
        JTextField name=field(30),location=field(30),date=field(30);JComboBox<String> category=new JComboBox<>(new String[]{"Electronics","Documents","Accessories","Books","Clothing","Keys","Other"});styleCombo(category);JTextArea desc=new JTextArea(4,30);desc.setBackground(WHITE);desc.setForeground(TEXT);desc.setCaretColor(BLUE);desc.setLineWrap(true);desc.setWrapStyleWord(true);desc.setBorder(BorderFactory.createLineBorder(BORDER));
        addFormRow(form,c,"Item name",name);addFormRow(form,c,"Category",category);addFormRow(form,c,lost?"Last seen location":"Found location",location);date.setText(LocalDate.now().toString());addFormRow(form,c,"Date (YYYY-MM-DD)",date);addFormRow(form,c,"Description",new JScrollPane(desc));
        c.gridy++;JButton submit=button(lost?"Submit Lost Item":"Submit Found Item");form.add(submit,c);submit.addActionListener(e->{if(name.getText().isBlank()||location.getText().isBlank()||date.getText().isBlank()||desc.getText().isBlank()){showMessage("Please complete all fields.");return;}try{LocalDate.parse(date.getText().trim());}catch(Exception ex){showMessage("Enter date in YYYY-MM-DD format.");return;}if(lost)service.addLostItem(name.getText().trim(),(String)category.getSelectedItem(),desc.getText().trim(),location.getText().trim(),date.getText().trim(),student);else service.addFoundItem(name.getText().trim(),(String)category.getSelectedItem(),desc.getText().trim(),location.getText().trim(),date.getText().trim(),student);showMessage("Your item report has been submitted.");showDashboard();});
        p.add(form,BorderLayout.CENTER);return p;
    }
    private void addFormRow(JPanel panel,GridBagConstraints c,String label,JComponent input){c.gridy++;panel.add(new JLabel(label),c);c.gridy++;panel.add(input,c);}

    private JPanel buildBrowsePage(Student student){
        JPanel p=page();p.add(heading("Browse Items"),BorderLayout.NORTH);JPanel top=new JPanel(new FlowLayout(FlowLayout.LEFT));top.setOpaque(false);JTextField search=field(22);JComboBox<String> filter=new JComboBox<>(new String[]{"All","Lost","Found","Returned"});styleCombo(filter);JButton find=button("Search");top.add(new JLabel("Search"));top.add(search);top.add(filter);top.add(find);p.add(top,BorderLayout.NORTH);
        DefaultTableModel model=tableModel(new String[]{"ID","Type","Item","Category","Location","Date","Status"});JTable table=new JTable(model);styleTable(table);Runnable refresh=()->{model.setRowCount(0);String q=search.getText().toLowerCase();String f=(String)filter.getSelectedItem();for(Item i:service.getItems()){if(!f.equals("All")&&!i.getStatus().equals(f))continue;if(!((i.getItemName()+" "+i.getCategory()+" "+i.getLocation()).toLowerCase().contains(q)))continue;model.addRow(new Object[]{i.getItemId(),i.getType(),i.getItemName(),i.getCategory(),i.getLocation(),i.getDate(),i.getStatus()});}};refresh.run();find.addActionListener(e->refresh.run());filter.addActionListener(e->refresh.run());
        JButton claim=button("Claim Selected Found Item");claim.addActionListener(e->{int row=table.getSelectedRow();if(row<0){showMessage("Select an item first.");return;}String id=model.getValueAt(table.convertRowIndexToModel(row),0).toString();Item item=service.findItem(id);if(item==null||!(item instanceof FoundItem)||!item.getStatus().equals("Found")){showMessage("Only available found items can be claimed.");return;}String message=JOptionPane.showInputDialog(this,"Explain why this item belongs to you:");if(message==null||message.isBlank())return;String error=service.submitClaim(item,student,message.trim());showMessage(error==null?"Claim request submitted. The admin will review it.":error);if(error==null)showDashboard();});
        JPanel bottom=new JPanel(new FlowLayout(FlowLayout.RIGHT));bottom.setOpaque(false);bottom.add(claim);p.add(new JScrollPane(table),BorderLayout.CENTER);p.add(bottom,BorderLayout.SOUTH);return p;
    }
    private JPanel buildMyReportsPage(Student student){JPanel p=page();p.add(heading("My Reports"),BorderLayout.NORTH);DefaultTableModel m=tableModel(new String[]{"ID","Type","Item","Category","Location","Date","Status"});JTable t=new JTable(m);styleTable(t);for(Item i:service.getItems())if(i.getReportedBy().equals(student.getUserId()))m.addRow(new Object[]{i.getItemId(),i.getType(),i.getItemName(),i.getCategory(),i.getLocation(),i.getDate(),i.getStatus()});p.add(new JScrollPane(t),BorderLayout.CENTER);return p;}
    private JPanel buildMyClaimsPage(Student student){JPanel p=page();p.add(heading("My Claims"),BorderLayout.NORTH);DefaultTableModel m=tableModel(new String[]{"Claim ID","Item ID","Item","Message","Status"});JTable t=new JTable(m);styleTable(t);for(ClaimRequest c:service.getClaims())if(c.getStudentId().equals(student.getUserId())){Item i=service.findItem(c.getItemId());m.addRow(new Object[]{c.getClaimId(),c.getItemId(),i==null?"Deleted item":i.getItemName(),c.getMessage(),c.getStatus()});}p.add(new JScrollPane(t),BorderLayout.CENTER);return p;}

    private JPanel buildAdminItemsPage(){JPanel p=page();p.add(heading("Manage Items"),BorderLayout.NORTH);DefaultTableModel m=tableModel(new String[]{"ID","Type","Item","Category","Location","Reporter","Status"});JTable t=new JTable(m);styleTable(t);for(Item i:service.getItems())m.addRow(new Object[]{i.getItemId(),i.getType(),i.getItemName(),i.getCategory(),i.getLocation(),service.getStudentName(i.getReportedBy()),i.getStatus()});JButton delete=button("Delete Selected Item");delete.addActionListener(e->{int r=t.getSelectedRow();if(r<0){showMessage("Select an item first.");return;}String id=m.getValueAt(t.convertRowIndexToModel(r),0).toString();int confirm=JOptionPane.showConfirmDialog(this,"Delete this item and its claim requests?","Confirm",JOptionPane.YES_NO_OPTION);if(confirm==JOptionPane.YES_OPTION){service.deleteItem(id);showDashboard();}});JPanel b=new JPanel(new FlowLayout(FlowLayout.RIGHT));b.setOpaque(false);b.add(delete);p.add(new JScrollPane(t),BorderLayout.CENTER);p.add(b,BorderLayout.SOUTH);return p;}
    private JPanel buildAdminClaimsPage(){JPanel p=page();p.add(heading("Claim Requests"),BorderLayout.NORTH);DefaultTableModel m=tableModel(new String[]{"Claim ID","Item ID","Item","Student","Message","Status"});JTable t=new JTable(m);styleTable(t);for(ClaimRequest c:service.getClaims()){Item i=service.findItem(c.getItemId());m.addRow(new Object[]{c.getClaimId(),c.getItemId(),i==null?"Deleted item":i.getItemName(),c.getStudentName(),c.getMessage(),c.getStatus()});}JButton approve=button("Approve Claim"),reject=button("Reject Claim"),returned=button("Mark Item Returned");approve.addActionListener(e->changeSelectedClaim(t,m,"Approved"));reject.addActionListener(e->changeSelectedClaim(t,m,"Rejected"));returned.addActionListener(e->{int r=t.getSelectedRow();if(r<0){showMessage("Select a claim first.");return;}String status=m.getValueAt(t.convertRowIndexToModel(r),5).toString();if(!status.equals("Approved")){showMessage("Approve the claim before marking the item returned.");return;}String id=m.getValueAt(t.convertRowIndexToModel(r),1).toString();service.markReturned(id);showMessage("Item marked as returned.");showDashboard();});JPanel b=new JPanel(new FlowLayout(FlowLayout.RIGHT));b.setOpaque(false);b.add(approve);b.add(reject);b.add(returned);p.add(new JScrollPane(t),BorderLayout.CENTER);p.add(b,BorderLayout.SOUTH);return p;}
    private void changeSelectedClaim(JTable t,DefaultTableModel m,String status){int r=t.getSelectedRow();if(r<0){showMessage("Select a claim first.");return;}String current=m.getValueAt(t.convertRowIndexToModel(r),5).toString();if(!current.equals("Pending")){showMessage("Only pending claims can be reviewed.");return;}String id=m.getValueAt(t.convertRowIndexToModel(r),0).toString();service.updateClaim(id,status);showMessage("Claim "+status.toLowerCase()+".");showDashboard();}
    private DefaultTableModel tableModel(String[] columns){return new DefaultTableModel(columns,0){@Override public boolean isCellEditable(int row,int column){return false;}};}
    private void styleTable(JTable table){table.setRowHeight(31);
        table.setFont(new Font("SansSerif", Font.PLAIN, 13));
        table.setForeground(TEXT);
        table.setBackground(WHITE);
        table.setGridColor(BORDER);
        table.setSelectionBackground(PALE_PINK);
        table.setSelectionForeground(TEXT);
        table.setFillsViewportHeight(true);
        table.getTableHeader().setFont(new Font("SansSerif", Font.BOLD, 13));
        table.getTableHeader().setForeground(TEXT);
        table.getTableHeader().setBackground(PALE_PINK);
        table.getTableHeader().setOpaque(true);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);}
}
