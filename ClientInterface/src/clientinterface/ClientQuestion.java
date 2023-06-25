/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package clientinterface;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JRadioButton;
import javax.swing.JRootPane;
import javax.swing.JToggleButton;

/**
 *
 * @author Arun Gupta
 */

public class ClientQuestion extends javax.swing.JFrame  implements ActionListener{
  //  JButton b[];
    BufferedReader brc, brc1;
    Socket c;
    PrintWriter out;
    String msg;
    int noq; 
    JRadioButton jr[][];
    JButton review[];
    JButton next[],prev[];
    Timer t;
    
    /**
     * Creates new form ClientQuestion
     */
    public ClientQuestion(Socket cl) {
        c=cl;
    setUndecorated(true);
     getRootPane().setWindowDecorationStyle(JRootPane.NONE);
     Toolkit tk=Toolkit.getDefaultToolkit();
     int xsize=((int)tk.getScreenSize().getWidth());
     int ysize=((int)tk.getScreenSize().getHeight());
     setSize(xsize, ysize);
         initComponents();
     
        
             try
       {
       brc=new BufferedReader(new InputStreamReader(c.getInputStream()));
//       brc1=new BufferedReader(new InputStreamReader(c.getInputStream()));
       out=new PrintWriter(c.getOutputStream(),true);
       
          System.out.println("head again created");
          // out.println("imagesend");
          String uname=brc.readLine();
           user.setText(uname);
           String no=brc.readLine();
           String time=brc.readLine();
           noq=Integer.parseInt(no);
           System.out.println(noq+  "   "+time);
           //if(msg.equals("OK"))
           //{
            
           //}
           jr=new JRadioButton[noq][4];
           next=new JButton[noq];
           review=new JButton[noq];
           prev=new JButton[noq];
             readQuestions();
          // readStudentImage();
         
             addButtons();
           addanswer("0");
         queslabel.setText("1");
           t=new Timer(time);
        t.start();
        UnusualActivity unsualActivity = new UnusualActivity(c);
        unsualActivity.start();
        
//             T op=new T(cl);
//             op.start();
            
        // readStudentImage();
         
        }catch(Exception e){System.out.println(e.getMessage());}
       
    }
    
    class T extends Thread
    {
        Socket soc;
        public T(Socket soc)
        {
            this.soc=soc;
        }
        public void run()
        {
         try
        {
     //  DataInputStream dis=new DataInputStream(c.getInputStream());
            BufferedReader br=new BufferedReader(new InputStreamReader(soc.getInputStream()));
           String ms=br.readLine();
       
                    //receive and save image from client
            System.out.println(ms);
                  String p="./studentphoto/"+ms.substring(ms.lastIndexOf("/")+1);
                  System.out.println(p);
                      
                    BufferedImage image=null;
            try {
                image = ImageIO.read(c.getInputStream());
            } catch (IOException ex) {
                Logger.getLogger(ClientQuestion.class.getName()).log(Level.SEVERE, null, ex);
            }
           sphoto.setIcon(new ImageIcon(image));
            
         
        }  catch(Exception ex)
          {
                  System.out.println(ex.getMessage());
          }   
        
        }
    }
    
    class UnusualActivity extends Thread{
        Socket ss;
        public UnusualActivity(Socket ss){
            this.ss=ss;
        }
        public void run(){
            System.out.println("Checking remove Active users msg from server");
            try{
                BufferedReader br=new BufferedReader(new InputStreamReader(ss.getInputStream()));
                if(br.readLine().equals("removeActiveUsers")){
                    System.out.print("Client received==>User Removed");
                    JOptionPane.showMessageDialog(rootPane, "Unusual activity found, server is closing you!!");
                    System.exit(0);
            }
            }catch(Exception e){}
        }
    }
   
    private void readQuestions()
    {
        try
        {
            int j=0;
        while(!(msg=brc.readLine()).equals("exit"))
        {
            System.out.println("msg="+msg);
            ques.put(String.valueOf(j), msg);
            msg=brc.readLine();
            System.out.println(msg);
            
             opt1.put(String.valueOf(j), msg);
             msg=brc.readLine();
            System.out.println(msg);
           
             opt2.put(String.valueOf(j), msg);
             msg=brc.readLine();
              System.out.println(msg);
           
             opt3.put(String.valueOf(j), msg);
              msg=brc.readLine();
              System.out.println(msg);
           
              opt4.put(String.valueOf(j), msg);
             j++;
        }  
        
        }catch(Exception e){}
    }
    private void readStudentImage()
    {
       
    }
    private void writeanswertoserver()
    {
       //out.println();
       int i=0,na=0; 
        while(i<noq)
        {
           if(ans.get(String.valueOf(i))!=null)
           {
          //  System.out.println(ans.get(String.valueOf(i)));
               out.println(i+":"+ans.get(String.valueOf(i)));
           }
           else
           {
               na++;
           }
            i++;
        }
        out.println("exit");
       
       
       new InformUserAttempt(noq,na).setVisible(true);
       this.setVisible(false);
         t.stop();
       
    }
private void addanswer(String v)
{
    qtext.setText(ques.get(v));
       anspanel.removeAll();
       anspanel.revalidate();
    anspanel.setLayout(new GridLayout(4,1));
    nextpanel.removeAll();
    nextpanel.revalidate();
    nextpanel.setLayout(new GridLayout(1,3));
        ButtonGroup bg=new ButtonGroup();
        int l=Integer.parseInt(v);
       // l=l-1;
       if(l<noq-1)
       {
       next[l]=new JButton("next");
       next[l].setBackground(Color.blue);
              
       next[l].setActionCommand("nextji"+(l+1));
       next[l].addActionListener(this);
       }
       else
       {
             next[l]=new JButton("submit");
             next[l].setBackground(Color.blue);
             next[l].setActionCommand("submitji");
            next[l].addActionListener(this);
     
       }
       
       review[l]=new JButton("Review");
       review[l].setBackground(Color.LIGHT_GRAY);
       review[l].setActionCommand("reviewji"+l);
       review[l].addActionListener(this);
       if(l!=0)
       {
            prev[l]=new JButton("Previos");
             prev[l].setBackground(Color.PINK);
             prev[l].setActionCommand("previousji"+(l-1));
            prev[l].addActionListener(this);
            nextpanel.add(prev[l]);
            nextpanel.add(next[l]);
            nextpanel.add(review[l]);
       }
       else
       {
                nextpanel.add(next[l]);
                nextpanel.add(review[l]);
       }
       jr[l][0]=new JRadioButton(opt1.get(v));
       jr[l][0].setFont(new Font("Times New Roman",Font.BOLD,14));
       System.out.println(opt1.get(v)+"  "+opt2.get(v)+"  "+opt3.get(v)+"   "+opt4.get(v));
       
       jr[l][1]=new JRadioButton(opt2.get(v));
     jr[l][1].setFont(new Font("Times New Roman",Font.BOLD,14));
       
       jr[l][2]=new JRadioButton(opt3.get(v));
       jr[l][2].setFont(new Font("Times New Roman",Font.BOLD,14));
       
       jr[l][3]=new JRadioButton(opt4.get(v));
       jr[l][3].setFont(new Font("Times New Roman",Font.BOLD,14));
       jr[l][0].setActionCommand("radioji"+l+":"+"0"+":"+"A");
       jr[l][1].setActionCommand("radioji"+l+":"+"1"+":"+"B");
        jr[l][2].setActionCommand("radioji"+l+":"+"2"+":"+"C");
        jr[l][3].setActionCommand("radioji"+l+":"+"3"+":"+"D");
       // JLabel jl[];
       /* jl=new JLabel[4];
        jl[0]=new JLabel("(A)");
        jl[1]=new JLabel("(B)");
        jl[2]=new JLabel("(C)");
        jl[3]=new JLabel("(D)");*/
       for(int i=0;i<4;i++)
       {
       // anspanel.add(jl[i]);
       anspanel.add(jr[l][i]);
       bg.add(jr[l][i]);
       jr[l][i].addActionListener(this);
           
       }
       
       System.out.println("radio button added");  
   

}

    @Override
    public void actionPerformed(ActionEvent e) {
   String v= e.getActionCommand();
        System.out.println(v);
   if(v.contains("buttonji"))
   {
       String l=v.substring(8);
     addanswer(String.valueOf(Integer.parseInt(l)));
       queslabel.setText(String.valueOf(Integer.parseInt(l)+1));
    System.out.println(v+"  action");
    if(ans.containsKey(l))
    {
        int k=Integer.parseInt(l);
        int m;
        System.out.println("value is containing");
        if(ans.get(l).equals("A"))
        {
            m=0;
        }
        else if(ans.get(l).equals("B"))
        {
            m=1;
        }
        else if(ans.get(l).equals("C"))
        {
            m=2;
        }
        else
            m=3;
      jr[k][m].setSelected(true);
      b[k].setBackground(Color.green);
    }
     
   }
   else if(v.contains("radioji"))
   {
        String l=v.substring(7,v.indexOf(":"));
        System.out.println("l is "+l);
             String j= v.substring(v.indexOf(":")+1,v.lastIndexOf(":"));;
             String k=v.substring(v.lastIndexOf(":")+1);
             System.out.println("l  : "+l+" j :"+j+" k  : "+k);
      if(!ans.containsKey(l))
    {
       
      //int k=Integer.parseInt(l)-1;
      //jr[k][Integer.parseInt(ans.get(l))-1].setSelected(true);
        ans.put(l, k);
        int m=Integer.parseInt(l);
        b[m].setBackground(Color.GREEN);
        System.out.println("value is "+ans.get(l));
        
    }
      else
      {
          ans.remove(l);
          ans.put(l,k);
          System.out.println("value replaced to answer "+k);
      }
   }
   else if(v.contains("nextji"))
   {
      String k= v.substring(6);
      addanswer(k);
      queslabel.setText(String.valueOf(Integer.parseInt(k)+1));
        if(ans.containsKey(k))
    {
        int p=Integer.parseInt(k);
        int m;
        System.out.println("value is containing");
        if(ans.get(k).equals("A"))
        {
            m=0;
        }
        else if(ans.get(k).equals("B"))
        {
            m=1;
        }
        else if(ans.get(k).equals("C"))
        {
            m=2;
        }
        else
            m=3;
      jr[p][m].setSelected(true);
      b[p].setBackground(Color.green);
    }
   }
   else if(v.contains("submitji"))
   {
       int i=JOptionPane.showConfirmDialog(this,"Are you sure want to submit??","Confirmation",JOptionPane.YES_NO_OPTION);
       if(i==0)
       {
       writeanswertoserver();
       }
   }
   else if(v.contains("previousji"))
   {
      String k= v.substring(10);
      addanswer(k);
      queslabel.setText(String.valueOf(Integer.parseInt(k)+1));
        if(ans.containsKey(k))
    {
        int p=Integer.parseInt(k);
        int m;
        System.out.println("value is containing");
        if(ans.get(k).equals("A"))
        {
            m=0;
        }
        else if(ans.get(k).equals("B"))
        {
            m=1;
        }
        else if(ans.get(k).equals("C"))
        {
            m=2;
        }
        else
            m=3;
      jr[p][m].setSelected(true);
      b[p].setBackground(Color.green);
    } 
   }
   else if(v.contains("reviewji"))
   {
       int l=Integer.parseInt(v.substring(8));
       b[l].setBackground(Color.YELLOW);
   }
   
    }
    
private void addButtons()
{
    
    bpanel.setLayout(new GridLayout((noq/5)+1,5));
 //  nextpanel.setLayout(new GridLayout(1,2));
    b=new JButton[noq];
    for(int i=0;i<noq;i++)
    {
      b[i]=new JButton(String.valueOf(i+1));
      b[i].setBackground(Color.red);
        b[i].setSize(1,1);
        bpanel.add(b[i]);
        b[i].setActionCommand("buttonji"+(i));
        b[i].addActionListener(this);
       
    }
 
}

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        sphoto = new javax.swing.JLabel();
        hlb = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        mlb = new javax.swing.JLabel();
        slb = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        user = new javax.swing.JLabel();
        alabel = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        qtext = new javax.swing.JTextArea();
        anspanel = new javax.swing.JPanel();
        nextpanel = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        queslabel = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        bpanel = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jLabel10 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);

        jLabel1.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(51, 51, 255));
        jLabel1.setText("Timer");

        hlb.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        hlb.setForeground(new java.awt.Color(255, 51, 51));

        jLabel4.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(0, 0, 255));
        jLabel4.setText(" :: ");

        jLabel5.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(0, 0, 255));
        jLabel5.setText(" :: ");

        mlb.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        mlb.setForeground(new java.awt.Color(255, 51, 51));

        slb.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        slb.setForeground(new java.awt.Color(255, 102, 102));

        jLabel2.setFont(new java.awt.Font("Times New Roman", 1, 24)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(0, 51, 204));
        jLabel2.setText("Welcome");

        user.setFont(new java.awt.Font("Times New Roman", 1, 24)); // NOI18N
        user.setForeground(new java.awt.Color(255, 0, 0));

        alabel.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(user, javax.swing.GroupLayout.PREFERRED_SIZE, 291, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 171, Short.MAX_VALUE)
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(hlb, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(mlb, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(slb, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(35, 35, 35))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(alabel, javax.swing.GroupLayout.PREFERRED_SIZE, 298, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addComponent(sphoto, javax.swing.GroupLayout.PREFERRED_SIZE, 205, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(24, 24, 24)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(sphoto, javax.swing.GroupLayout.PREFERRED_SIZE, 134, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(alabel, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(hlb, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(mlb, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(slb, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(61, 61, 61)
                        .addComponent(user, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        qtext.setEditable(false);
        qtext.setBackground(new java.awt.Color(204, 204, 255));
        qtext.setColumns(20);
        qtext.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        qtext.setRows(5);
        qtext.setAutoscrolls(false);
        jScrollPane1.setViewportView(qtext);

        javax.swing.GroupLayout anspanelLayout = new javax.swing.GroupLayout(anspanel);
        anspanel.setLayout(anspanelLayout);
        anspanelLayout.setHorizontalGroup(
            anspanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 218, Short.MAX_VALUE)
        );
        anspanelLayout.setVerticalGroup(
            anspanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout nextpanelLayout = new javax.swing.GroupLayout(nextpanel);
        nextpanel.setLayout(nextpanelLayout);
        nextpanelLayout.setHorizontalGroup(
            nextpanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 227, Short.MAX_VALUE)
        );
        nextpanelLayout.setVerticalGroup(
            nextpanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );

        jLabel7.setFont(new java.awt.Font("Times New Roman", 1, 24)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(0, 51, 204));
        jLabel7.setText("Question :");

        queslabel.setFont(new java.awt.Font("Times New Roman", 1, 24)); // NOI18N
        queslabel.setForeground(new java.awt.Color(0, 51, 204));

        jLabel3.setText("    (A)");

        jLabel8.setText("    (B)");

        jLabel9.setText("    (C)");

        jLabel14.setText("    (D)");

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(4, 4, 4)
                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 655, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 141, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(queslabel, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                        .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(anspanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(nextpanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(queslabel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 165, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(56, 56, 56)
                        .addComponent(nextpanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(anspanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(19, 19, 19))))
        );

        bpanel.setBackground(new java.awt.Color(255, 204, 204));

        javax.swing.GroupLayout bpanelLayout = new javax.swing.GroupLayout(bpanel);
        bpanel.setLayout(bpanelLayout);
        bpanelLayout.setHorizontalGroup(
            bpanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 259, Short.MAX_VALUE)
        );
        bpanelLayout.setVerticalGroup(
            bpanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 211, Short.MAX_VALUE)
        );

        jLabel6.setFont(new java.awt.Font("Times New Roman", 1, 24)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(0, 51, 204));
        jLabel6.setText("Question Section");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(bpanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 229, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(38, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addComponent(bpanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jLabel10.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(255, 0, 51));
        jLabel10.setText("Read instructions carefully...");

        jButton1.setBackground(new java.awt.Color(255, 0, 0));

        jButton2.setBackground(new java.awt.Color(0, 204, 0));

        jButton3.setBackground(new java.awt.Color(255, 255, 0));

        jLabel11.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(51, 255, 0));
        jLabel11.setText("Solved Question");

        jLabel12.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(255, 0, 51));
        jLabel12.setText("Not Solved Question");

        jLabel13.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(255, 255, 0));
        jLabel13.setText("Review to Solve Later");

        jTextArea1.setEditable(false);
        jTextArea1.setBackground(new java.awt.Color(0, 204, 204));
        jTextArea1.setColumns(20);
        jTextArea1.setFont(new java.awt.Font("Ubuntu", 1, 15)); // NOI18N
        jTextArea1.setForeground(new java.awt.Color(0, 0, 255));
        jTextArea1.setRows(5);
        jTextArea1.setText("  1-\tTo Submit Answer Go to last Question and Submit Answers.\n  2-\tStudent can navigate to next question by clicking next button\n\tor by clicking on Question Section Button.\n  3-\tBox color for solved, not solved, review to solve letter are given \n\ton left side.");
        jTextArea1.setAutoscrolls(false);
        jTextArea1.setFocusable(false);
        jScrollPane2.setViewportView(jTextArea1);

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(53, 53, 53)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, 30, Short.MAX_VALUE)
                    .addComponent(jButton2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(28, 28, 28)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 161, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel13, javax.swing.GroupLayout.DEFAULT_SIZE, 180, Short.MAX_VALUE))
                .addGap(58, 58, 58)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 237, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 623, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(45, 45, 45)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(9, 9, 9)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(9, 9, 9)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel13, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(49, 49, 49))
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(11, 11, 11)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(31, 31, 31)
                                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(20, 20, 20))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(46, 46, 46)
                .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * @param args the command line arguments
     */
 

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel alabel;
    private javax.swing.JPanel anspanel;
    private javax.swing.JPanel bpanel;
    private javax.swing.JLabel hlb;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JLabel mlb;
    private javax.swing.JPanel nextpanel;
    private javax.swing.JTextArea qtext;
    private javax.swing.JLabel queslabel;
    private javax.swing.JLabel slb;
    private javax.swing.JLabel sphoto;
    private javax.swing.JLabel user;
    // End of variables declaration//GEN-END:variables
static Hashtable<String,String> ans=new Hashtable<String,String>();
static Hashtable<String,String> ques=new Hashtable<String,String>();
static Hashtable<String,String> opt1=new Hashtable<String,String>();
static Hashtable<String,String> opt2=new Hashtable<String,String>();
static Hashtable<String,String> opt3=new Hashtable<String,String>();
static Hashtable<String,String> opt4=new Hashtable<String,String>();
static Hashtable<String,String> opt5=new Hashtable<String,String>();
JButton b[];

class Timer extends Thread
{
    int ta1,ta2;
    int hh;
    int min;
    int sec;
    int i;
    int ttime;
    public Timer(String time)
    {
      //  System.out.println(ho +"  "+mi);
        hh=0;
        min=0;
        hlb.setText("0");
        mlb.setText("0");
        slb.setText("0");
        ttime=Integer.parseInt(time);
        ta1=ttime-(ttime-1);
        ta2=ttime-(ttime-2);
    }
    public void run()
    {
                while(ttime>0)
        {
            for(sec=1;sec<=59;sec++)
            {
                try{  sleep(1000);    }catch(Exception e){}
                //sec++;
                //System.out.println(sec);
                slb.setText(String.valueOf(sec));
                
            }
            if(sec==60)
            {
                sec=0;
                min++;
                 slb.setText(String.valueOf(sec));
                mlb.setText(String.valueOf(min));
                
               //  System.out.println(min);
            }
            if(min==59)
            {
                hh++;
                min=0;
                hlb.setText(String.valueOf(hh));
              
                mlb.setText(String.valueOf(min));
             //  System.out.println(hh);
            }
            
            ttime--;
            if(ttime==ta1)
            {
                alabel.setText("You have 1 Minute Left Hurry Up!!!");
            }
            if(ttime==ta2)
            {
                alabel.setText("You have 2 Minute Left Hurry Up!!!");
            }
        }
      writeanswertoserver();
    }
}
}
