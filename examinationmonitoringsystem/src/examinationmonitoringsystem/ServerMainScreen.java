/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package examinationmonitoringsystem;

import java.awt.Color;
import java.awt.Component;
import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import static java.lang.Thread.sleep;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;

/**
 *
 * @author Rohit Kesarwani
 */
public class ServerMainScreen extends javax.swing.JFrame {

    /**
     * Creates new form ServerMainScreen
     */
    class ServerThread extends Thread
    {

        class ProcessClients extends Thread
        {
            private int threadid;
            private String userid,pcode;
            private Socket cl;
            private String user;
            private int socketObjPosition;
            public ProcessClients(int threadid,String userid,Socket cl,String pcode, int socketObjPosition)
            {
                this.threadid=threadid;
                this.userid=userid;
                this.cl=cl;
                this.pcode=pcode;
                this.socketObjPosition=socketObjPosition;
            }
            public void run()
            {
                BufferedReader brc;
                PrintWriter out;
                String msg;
                try
                {
                    addActiveInfo(threadid,userid,socketObjPosition,"User Logging In");
                    brc=new BufferedReader(new InputStreamReader(cl.getInputStream()));
                    out=new PrintWriter(cl.getOutputStream(), true);
                    System.out.println("head created at server");
                  //  msg=brc.readLine();
                    writeUserName();
                    writeQuestions();
                    writeStudentImage();
                 //  ImageWritetoClient iw=new ImageWritetoClient(cl);
                   // iw.start();
                    updateActiveInfo(threadid,user+" Solving Question ");
                    
                    readAnswerFromClient();
                    
                        
                         
                     
                    updateActiveInfo(threadid,user+" is Logging Out");
                   // addLoginInfo(threadid);
                    deleteActiveInfo(threadid);

 
                }catch(Exception e){}
            }
            private void writeUserName()
            {
                try
                {
                Statement s=dh.connect();
               
                ResultSet rs=s.executeQuery("select name from s_registration where uroll='"+userid+"'");
                rs.next();
                user=rs.getString(1);
                out.println(user);
                    System.out.println("");
                }catch(Exception e){}
            }
            private void writeQuestions()
            {
                try
                {
                Statement s=dh.connect();
               
                ResultSet rs=s.executeQuery("select noq,time from qdetails where password='"+pcode+"'");
                rs.next();
                out.println(rs.getString(1));
                out.println(rs.getString(2));
              //  out.println("enter");
                    ResultSet rs4=s.executeQuery("select ques,A,B,C,D from questions"+pcode+" order by rand()");
                    while(rs4.next())
                    {
                        for(int j=1;j<=5;j++)
                        {
                            out.println(rs4.getString(j));
                            System.out.println(rs4.getString(j));
                        }
                    }
                    out.println("exit");
                
                
                }catch(Exception w){System.out.println(w.getMessage());}
            }
            private void writeStudentImage()
            {
                
            }
            private void readAnswerFromClient()
            {
                try
                {
                String a;
                //int 
                while(!(a=brc.readLine()).equals("exit"))
                {
                    ans.add(a);
                }
                    System.out.println("adding to vector");
                writeAnswerToDB();
                }catch(Exception e){}
            }
            private void writeAnswerToDB()
            {
                Statement s=dh.connect();
                ResultSet r;
                String tab="questions"+pcode;
                System.out.println("Enter db section");
                try
                {
                int i=0,rn=0;
                    System.out.println("LOOOp");
                while(i<ans.size())
                {
                    String ind=ans.get(i).substring(0,ans.get(i).indexOf(":"));
                       String an=ans.get(i).substring(ans.get(i).indexOf(":")+1);
                       System.out.println(ind +":" + an);
                    r=s.executeQuery("select count(*) from "+tab+" where rans='"+an+"' and qid='"+ind+"'");
                    r.next();
                    System.out.println(r.getInt(1));
                    if(r.getInt(1)>0)
                    {
                        rn++;
                    }
                    i++;
                }
                 r=s.executeQuery("select noq from qdetails where password='"+pcode+"'");
                 r.next();
                    System.out.println("noq is"+r.getString(1));
                double per=(rn*100)/Integer.parseInt(r.getString(1));
                    System.out.println("percentage is"+per);
                s.executeUpdate("insert into studresult"+pcode+" values('"+userid+"','"+ans.size()+"',now(),'"+per+"',"+r.getString(1)+","+rn+")");
                    System.out.println("inserted"); 
                dh.commit();
                dh.disconnect();
                }catch(Exception e){
                    System.out.println(e.getMessage());}
            }
            private void addActiveInfo(int tid,String userid, int socketObjPosition, String msg)
            {
             Connection c=null;
            Statement st;
            ResultSet r;

            try
            {
                 
                st=dh.connect();
                java.util.Date d=new java.util.Date();
                DateFormat dt=DateFormat.getTimeInstance(DateFormat.SHORT);
                st.executeUpdate("insert into Active_Login values("+tid+",'"+userid+"',now(),'"+dt.format(d)+"','"+msg+"', "+socketObjPosition+")");
                c.commit();
                System.out.println("AddActive Info success");

            }catch(Exception e){System.out.println(e.getMessage());}
            finally
            {
                try{c.close();}catch(Exception e){}

            }
            }
            private void addLoginInfo(int tid)
            {
             Connection c=null;
            Statement st;
            ResultSet r;
            String intime,outtime;
            try
            {
               
                     st=dh.connect();
                java.util.Date d=new java.util.Date();
                DateFormat dt=DateFormat.getTimeInstance(DateFormat.SHORT);
                outtime=dt.format(d);
                r=st.executeQuery("select ltime from Active_Login where tid="+tid);
                r.next();
                intime=r.getString(1);
                r.close();
                
                st.executeUpdate("insert into Active_Login values('"+userid+"',now(),'"+intime+"','"+outtime+"')");
                c.commit();

            }catch(Exception e){System.out.println(e.getMessage());}
            finally
            {
                try{c.close();}catch(Exception e){}

            }
            }
            private void updateActiveInfo(int tid,String msg)
            {
             Connection c=null;
            Statement st;
            ResultSet r;

            try
            {
                
                 st=dh.connect();

                st.executeUpdate("update Active_Login set COper='"+msg+"' where tid="+tid);
                c.commit();

            }catch(Exception e){System.out.println(e.getMessage());}
            finally
            {
                try{c.close();}catch(Exception e){}

            }
            }
            private void deleteActiveInfo(int tid)
            {
             Connection c=null;
            Statement st;
            ResultSet r;

            try
            {
                st=dh.connect();

                st.executeUpdate("delete from Active_Login where tid="+tid);
                c.commit();

            }catch(Exception e){System.out.println(e.getMessage());}
            finally
            {
                try{c.close();}catch(Exception e){}

            }
            }
         
            
            class ImageWritetoClient extends Thread
            
            {
                Socket soc;
                public ImageWritetoClient(Socket soc)
                {
                    this.soc=soc;
                }
                public void run()
                {
                    try
                {
                    PrintWriter  prt=new PrintWriter(soc.getOutputStream());
                    Statement s=dh.connect();
                    ResultSet r=s.executeQuery("select photo from s_registration where uroll="+userid);
                    r.next();
                    
                    String pat=r.getString(1);
                    System.out.println(pat);
                    String ex=pat.substring(pat.lastIndexOf(".")+1).toUpperCase();
                    System.out.println(ex);
                    prt.println(pat);
                    BufferedImage bi=ImageIO.read(new File(pat));
                    ImageIO.write(bi,ex,soc.getOutputStream());
                    
                }catch(Exception e){System.out.println(e.getMessage());}
                }
            }
        }
        public BufferedReader brc;
        public PrintWriter out;
        private boolean  runsubthreads;
        public ProcessClients p;
        
        public void run()
        {   
            int tid=1;
            runsubthreads=true;
            String userid,pass;
            while(runthread)
            {
               try
               {
                   client=s.accept();
                   
                   brc=new BufferedReader(new InputStreamReader(client.getInputStream()));
                   out=new PrintWriter(client.getOutputStream(), true);
                   System.out.println("connection receied");
                   socketObjectPosition+=1;
                   userid=brc.readLine();
                   pass=brc.readLine();
                   System.out.println(userid+pass);
                   int test=verifyUser(userid, pass);
                   if(test==1)
                   {
                       out.println("OK");
                       list.add(client);
                       System.out.println("List="+list);
                       p=new ProcessClients(tid, userid, client,pass, socketObjectPosition);
                       p.start();
                       
                   }
                   else if(test==0)
                   {
                       out.println("FAIL");
                       continue;
                   }
                   else if(test==2)
                   {
                       out.println("ALREADY"); continue;
                   }

               }catch(Exception e){}
               tid++;
            }
            runsubthreads=false;
        }
        private int verifyUser(String userid,String pass)
        {
            Connection c=null;
            Statement st;
            ResultSet r,rs;
            //boolean ans=false;
            int ans=0;
            try
            {
                st=dh.connect();
                r=st.executeQuery("select count(*) from s_registration s,qdetails q where s.uroll='"+userid.trim()+"' and q.password='"+pass+"'");
                r.next();
                int count;
                count=r.getInt(1);
                rs=st.executeQuery("select count(*) from studresult"+pass+" where uroll='"+userid.trim()+"'");
                rs.next();
                int con=rs.getInt(1);
                System.out.println("count is "+count);
                System.out.println("count of con is"+con);
                r.close();
                if(count==1&&con==0)
                {
                    ans=1;
                    System.out.println("true");
                }
                else if(count==0)
                {
                    ans=0;
                    System.out.println("false");
                }
                else if(count==1&&con==1)
                {
                    ans=2;
                    System.out.println("already");
                }
            }catch(Exception e){System.out.println(e.getMessage());}
            finally
            {
                try{c.close();}catch(Exception e){}
                return(ans);
            }
        }
    }
    
    
    public static ArrayList<Socket> list;
    public static int socketObjectPosition = 0;       //to get socket object position from database
    private ServerSocket s;
    private String serverip;
    public Socket client;
    private boolean runthread;
    public ServerThread sthread;
    public ServerMainScreen() {
        initComponents();
        serverip="127.0.0.1";
        try
        {
            list=new ArrayList<Socket>();
            //System.out.println("list ="+list);
        s=new ServerSocket(5000);
         //   UIManager.setLookAndFeel("com.sun.java.swing.plaf.motif.MotifLookAndFeel");
        Statement s=dh.connect();
        s.executeUpdate("delete from Active_Login");
        dh.commit();
      
        }catch(Exception e){}
        runthread=true;
        sthread=new ServerThread();
        sthread.start();
        ralist=new RefreashActiveList();
        ralist.start();
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jMenu3 = new javax.swing.JMenu();
        jMenuBar2 = new javax.swing.JMenuBar();
        jMenu4 = new javax.swing.JMenu();
        jMenu5 = new javax.swing.JMenu();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        activeu = new javax.swing.JTextField();
        jInternalFrame1 = new javax.swing.JInternalFrame();
        jScrollPane1 = new javax.swing.JScrollPane();
        activelist = new javax.swing.JTable();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenuItem1 = new javax.swing.JMenuItem();
        jMenuItem2 = new javax.swing.JMenuItem();
        removeActiveUsers = new javax.swing.JMenuItem();
        jMenu2 = new javax.swing.JMenu();
        jMenu6 = new javax.swing.JMenu();
        jMenuItem5 = new javax.swing.JMenuItem();
        jMenu7 = new javax.swing.JMenu();
        jMenuItem8 = new javax.swing.JMenuItem();
        jMenu8 = new javax.swing.JMenu();
        jMenuItem9 = new javax.swing.JMenuItem();

        jMenu3.setText("jMenu3");

        jMenu4.setText("File");
        jMenuBar2.add(jMenu4);

        jMenu5.setText("Edit");
        jMenuBar2.add(jMenu5);

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 48)); // NOI18N
        jLabel1.setText("Main Server Monitor");

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel2.setText("Active Users");

        activeu.setEditable(false);
        activeu.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        activeu.setForeground(new java.awt.Color(0, 0, 204));
        activeu.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        activeu.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 153), 2, true));

        jInternalFrame1.setTitle("Active Users [Activity Tracking]");
        jInternalFrame1.setVisible(true);

        activelist.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        activelist.setForeground(new java.awt.Color(0, 102, 51));
        activelist.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Thread ID", "Roll No", "Current Operation", "Student Image"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        activelist.setRowHeight(50);
        activelist.getTableHeader().setReorderingAllowed(false);
        jScrollPane1.setViewportView(activelist);

        jInternalFrame1.getContentPane().add(jScrollPane1, java.awt.BorderLayout.CENTER);

        jMenu1.setForeground(new java.awt.Color(0, 153, 153));
        jMenu1.setMnemonic('S');
        jMenu1.setText("Student ");
        jMenu1.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N

        jMenuItem1.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_N, java.awt.event.InputEvent.CTRL_MASK));
        jMenuItem1.setText("Register Student");
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem1);

        jMenuItem2.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_D, java.awt.event.InputEvent.CTRL_MASK));
        jMenuItem2.setText("Remove Student");
        jMenuItem2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem2ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem2);

        removeActiveUsers.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_X, java.awt.event.InputEvent.CTRL_MASK));
        removeActiveUsers.setText("Remove Active Users");
        removeActiveUsers.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                removeActiveUsersActionPerformed(evt);
            }
        });
        jMenu1.add(removeActiveUsers);

        jMenuBar1.add(jMenu1);
        jMenuBar1.add(jMenu2);

        jMenu6.setForeground(new java.awt.Color(0, 153, 51));
        jMenu6.setMnemonic('Q');
        jMenu6.setText("Question");
        jMenu6.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N

        jMenuItem5.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_Q, java.awt.event.InputEvent.ALT_MASK));
        jMenuItem5.setText("Compose");
        jMenuItem5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem5ActionPerformed(evt);
            }
        });
        jMenu6.add(jMenuItem5);

        jMenuBar1.add(jMenu6);

        jMenu7.setForeground(new java.awt.Color(0, 153, 51));
        jMenu7.setMnemonic('R');
        jMenu7.setText("Result");
        jMenu7.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N

        jMenuItem8.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_P, java.awt.event.InputEvent.CTRL_MASK));
        jMenuItem8.setText("View Result");
        jMenuItem8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem8ActionPerformed(evt);
            }
        });
        jMenu7.add(jMenuItem8);

        jMenuBar1.add(jMenu7);

        jMenu8.setForeground(new java.awt.Color(0, 153, 51));
        jMenu8.setMnemonic('X');
        jMenu8.setText("Exit");
        jMenu8.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N

        jMenuItem9.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_X, java.awt.event.InputEvent.ALT_MASK));
        jMenuItem9.setText("Close Application");
        jMenuItem9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem9ActionPerformed(evt);
            }
        });
        jMenu8.add(jMenuItem9);

        jMenuBar1.add(jMenu8);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(49, 49, 49)
                        .addComponent(jLabel2)
                        .addGap(18, 18, 18)
                        .addComponent(activeu, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jInternalFrame1, javax.swing.GroupLayout.Alignment.TRAILING))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(31, 31, 31)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2)
                            .addComponent(activeu, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(1, 1, 1)
                .addComponent(jInternalFrame1, javax.swing.GroupLayout.DEFAULT_SIZE, 410, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed
    RegStudent r=new RegStudent();
    r.setVisible(true);
    }//GEN-LAST:event_jMenuItem1ActionPerformed

    private void jMenuItem5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem5ActionPerformed
         QuesDetails q=new QuesDetails();
    q.setVisible(true);
    }//GEN-LAST:event_jMenuItem5ActionPerformed

    private void jMenuItem2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem2ActionPerformed
     RemUser r=new RemUser();
     r.setVisible(true);
    }//GEN-LAST:event_jMenuItem2ActionPerformed

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing

int i=JOptionPane.showConfirmDialog(this,"Are you sure Want to shutdown Server","Server Shutdown confirmation",JOptionPane.YES_NO_OPTION);
if(i==0)
{
    System.exit(0);
}

    }//GEN-LAST:event_formWindowClosing

    private void jMenuItem8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem8ActionPerformed
         // TODO add your handling code here:
         ViewResult viewResult = new ViewResult();
         viewResult.setVisible(runthread);
    }//GEN-LAST:event_jMenuItem8ActionPerformed

    private void jMenuItem9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem9ActionPerformed
int i=JOptionPane.showConfirmDialog(this,"Are you sure want to close server","Confirmation",JOptionPane.YES_NO_OPTION);
if(i==0)
{
        System.exit(0);
}
    }//GEN-LAST:event_jMenuItem9ActionPerformed

    private void removeActiveUsersActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_removeActiveUsersActionPerformed
        // TODO add your handling code here:
       
        //
        RemActiveUsers remActiveUsers = new RemActiveUsers();
        remActiveUsers.setVisible(runthread);
        //}catch(Exception e){}
    }//GEN-LAST:event_removeActiveUsersActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(ServerMainScreen.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ServerMainScreen.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ServerMainScreen.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ServerMainScreen.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
            
                    try{UIManager.setLookAndFeel("com.sun.java.swing.plaf.motif.MotifLookAndFeel");}catch(Exception e){}
                new ServerMainScreen().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTable activelist;
    private javax.swing.JTextField activeu;
    private javax.swing.JInternalFrame jInternalFrame1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenu jMenu3;
    private javax.swing.JMenu jMenu4;
    private javax.swing.JMenu jMenu5;
    private javax.swing.JMenu jMenu6;
    private javax.swing.JMenu jMenu7;
    private javax.swing.JMenu jMenu8;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuBar jMenuBar2;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JMenuItem jMenuItem5;
    private javax.swing.JMenuItem jMenuItem8;
    private javax.swing.JMenuItem jMenuItem9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JMenuItem removeActiveUsers;
    // End of variables declaration//GEN-END:variables
    ArrayList<String> ans=new ArrayList<>();
    DatabaseHelper dh=new DatabaseHelper();
   private RefreashActiveList ralist;
   
    class RefreashActiveList extends Thread
    {
        public void run()
        {
               Connection c=null;
               Statement st;
               ResultSet r;
               while(runthread)
               {
               try
               {
                   String ip,user,password;
                   st=dh.connect();
                   r=st.executeQuery("select count(*) from Active_Login");
                   r.next();
                   activeu.setText(r.getString(1));
                   r.close();
                   r=st.executeQuery("select a.tid,a.userid,a.COper,u.photo from Active_Login a,s_registration u where u.uroll=a.userid order by a.tid");

                   while(activelist.getRowCount()>0)
                   {
                      ((DefaultTableModel) activelist.getModel()).removeRow(0);
                   }
                   int row=0;
                   activelist.getColumnModel().getColumn(3).setCellRenderer(activelist.getDefaultRenderer(ImageIcon.class));
                   ImageIcon format=null;
                   java.awt.Image originalImage=null;
                   java.awt.Image scaledImage=null;
                   javax.swing.ImageIcon newIconImage=null;
                   while(r.next())
                   {
                       String t,u,co,p;
                       t=r.getString(1);
                       u=r.getString(2);
                       co=r.getString(3);
                       p=r.getString(4);
                       System.out.println("path is "+p);
                       format=new ImageIcon(p);
                       originalImage=format.getImage();
                       scaledImage = originalImage.getScaledInstance(60, 60, java.awt.Image.SCALE_SMOOTH);
                       newIconImage = new javax.swing.ImageIcon(scaledImage);
                       ((DefaultTableModel) activelist.getModel()).addRow(new Object[]{t,u,co,newIconImage});

                       //activelist.getModel().setValueAt(r.getObject(1), row, 0);
                       //activelist.getModel().setValueAt(r.getObject(2), row, 1);
                       //activelist.getModel().setValueAt(r.getObject(3), row, 2);
                       //activelist.getModel().setValueAt(new JLabel(new ImageIcon("d:/jyoti.jpg")), row, 3);
                       row++;
                   }
                   r.close();
               }catch(Exception e){System.out.println(e.getMessage());}
               finally
               {
                   try{c.close();}catch(Exception e){}
               }
               try{sleep(2000);}catch(Exception e){}
               }
        }
    }
    }


