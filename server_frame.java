package chat_server;

import java.io.*;                                   //Librerias
import java.net.*;
import java.util.*;

public class server_frame extends javax.swing.JFrame 
{
   ArrayList clientOutputStreams;                   //Arreglo de lista de clientes
   ArrayList<String> users;                         //Arreglo de usuarios, tipo cadena

   public class ClientHandler implements Runnable	      //Clase cliente
   {
       BufferedReader reader;                               //Lector de Buffer
       Socket sock;                                         //Socket con el cual vamos utilizar para conectar
       PrintWriter client;                                  //Muestra los mensajes del cliente

       public ClientHandler(Socket clientSocket, PrintWriter user) 
       {
            client = user;
            try 
            {
                sock = clientSocket;
                InputStreamReader isReader = new InputStreamReader(sock.getInputStream());
                reader = new BufferedReader(isReader);
            }
            catch (Exception ex) 
            {
                ta_chat.append("Error inesperado \n");
            }

       }

       @Override                                                //Sobre escribe el metodo run
       public void run() 
       {
            String message, connect = "Conectando", disconnect = "Desconectando", chat = "Chat" ;
            String[] data;

            try 
            {
                while ((message = reader.readLine()) != null) 
                {
                    ta_chat.append("Recibiendo: " + message + "\n");
                    data = message.split(":");
                    
                    for (String token:data) 
                    {
                        ta_chat.append(token + "\n");
                    }

                    if (data[2].equals(connect)) 
                    {
                        tellEveryone((data[0] + ":" + data[1] + ":" + chat));
                        userAdd(data[0]);
                    } 
                    else if (data[2].equals(disconnect)) 
                    {
                        tellEveryone((data[0] + ":se ha desconectado." + ":" + chat));
                        userRemove(data[0]);
                    } 
                    else if (data[2].equals(chat)) 
                    {
                        tellEveryone(message);
                    } 
                    else 
                    {
                        ta_chat.append("Sin condiciones para establecer conexion. \n");
                    }
                } 
             } 
             catch (Exception ex) 
             {
                ta_chat.append("Conexion perdida. \n");
                ex.printStackTrace();
                clientOutputStreams.remove(client);
             } 
	} 
    }

    public server_frame() 
    {
        initComponents();
    }

    @SuppressWarnings("No revisado")                     
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        ta_chat = new javax.swing.JTextArea();
        b_start = new javax.swing.JButton();
        b_end = new javax.swing.JButton();
        b_users = new javax.swing.JButton();
        b_clear = new javax.swing.JButton();
        lb_name = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Proyecto 2 Java");
        setName("Servidor"); // 
        setResizable(false);

        ta_chat.setColumns(20);
        ta_chat.setRows(5);
        jScrollPane1.setViewportView(ta_chat);

        b_start.setText("Iniciando");
        b_start.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                b_startActionPerformed(evt);
            }
        });

        b_end.setText("Finalizando");
        b_end.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                b_endActionPerformed(evt);
            }
        });

        b_users.setText("Usuarios en linea");
        b_users.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                b_usersActionPerformed(evt);
            }
        });

        b_clear.setText("Limpiando");
        b_clear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                b_clearActionPerformed(evt);
            }
        });

        lb_name.setText("Frame");
        lb_name.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(b_end, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(b_start, javax.swing.GroupLayout.DEFAULT_SIZE, 75, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 291, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(b_clear, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(b_users, javax.swing.GroupLayout.DEFAULT_SIZE, 103, Short.MAX_VALUE))))
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(lb_name)
                .addGap(209, 209, 209))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 340, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(b_start)
                    .addComponent(b_users))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(b_clear)
                    .addComponent(b_end))
                .addGap(4, 4, 4)
                .addComponent(lb_name))
        );

        pack();
    }// </editor-fold>//Al finalizar generar:initComponents

    private void b_endActionPerformed(java.awt.event.ActionEvent evt) {//Generando primero:event_b_endActionPerformed
        try 
        {
            Thread.sleep(5000);                 //5000 miisegundos = 5 segundos
        } 
        catch(InterruptedException ex) {Thread.currentThread().interrupt();}
        
        tellEveryone("El servidor se ha detenido y todos los usuarios se desconectaran.\n:Chat");
        ta_chat.append("Servidor deteniendose... \n");
        
        ta_chat.setText("");
    }//Generar al ultimo:event_b_endActionPerformed

    private void b_startActionPerformed(java.awt.event.ActionEvent evt) {//GEN-Primero:event_b_startActionPerformed
        Thread starter = new Thread(new ServerStart());
        starter.start();
        
        ta_chat.append("Servidor iniciando...\n");
    }//Generar al ultimo:event_b_startActionPerformed

    private void b_usersActionPerformed(java.awt.event.ActionEvent evt) {//GEN-Primero:event_b_usersActionPerformed
        ta_chat.append("\n Usuarios en linea : \n");
        for (String current_user : users)
        {
            ta_chat.append(current_user);
            ta_chat.append("\n");
        }    
        
    }

    private void b_clearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-Primero:event_b_clearActionPerformed
        ta_chat.setText("");
    }//Generar al ultimo:event_b_clearActionPerformed

    public static void main(String args[]) 
    {
        java.awt.EventQueue.invokeLater(new Runnable() 
        {
            @Override
            public void run() {
                new server_frame().setVisible(true);
            }
        });
    }
    
    public class ServerStart implements Runnable 
    {
        @Override
        public void run() 
        {
            clientOutputStreams = new ArrayList();
            users = new ArrayList();  

            try 
            {
                ServerSocket serverSock = new ServerSocket(2222);

                while (true) 
                {
				Socket clientSock = serverSock.accept();
				PrintWriter writer = new PrintWriter(clientSock.getOutputStream());
				clientOutputStreams.add(writer);

				Thread listener = new Thread(new ClientHandler(clientSock, writer));
				listener.start();
				ta_chat.append("Conexion realizada. \n");
                }
            }
            catch (Exception ex)
            {
                ta_chat.append("Error al conectar. \n");
            }
        }
    }
    
    public void userAdd (String data)                           //Metodo que agrega un nuevo usuario
    {
        String message, add = ": :Conectado", done = "Servidor: :Hecho", name = data;
        ta_chat.append("Antes " + name + " agregado. \n");
        users.add(name);
        ta_chat.append("Despues " + name + " agregado. \n");
        String[] tempList = new String[(users.size())];
        users.toArray(tempList);

        for (String token:tempList) 
        {
            message = (token + add);
            tellEveryone(message);
        }
        tellEveryone(done);
    }
    
    public void userRemove (String data)                    //Elimina un usuario
    {
        String message, add = ": :Conectado", done = "Servidor: :Hecho", name = data;
        users.remove(name);
        String[] tempList = new String[(users.size())];
        users.toArray(tempList);

        for (String token:tempList) 
        {
            message = (token + add);
            tellEveryone(message);
        }
        tellEveryone(done);
    }
    
    public void tellEveryone(String message) 
    {
	Iterator it = clientOutputStreams.iterator();

        while (it.hasNext()) 
        {
            try 
            {
                PrintWriter writer = (PrintWriter) it.next();
		writer.println(message);
		ta_chat.append("Enviando: " + message + "\n");
                writer.flush(); 
                ta_chat.setCaretPosition(ta_chat.getDocument().getLength());

            } 
            catch (Exception ex) 
            {
		ta_chat.append("Error enviando mensaje. \n");
            }
        } 
    }
    
    // Declaracion de variables - Si se modifican, cambia el funcionamiento del programa//GEN-Inicio:variables
    private javax.swing.JButton b_clear;
    private javax.swing.JButton b_end;
    private javax.swing.JButton b_start;
    private javax.swing.JButton b_users;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lb_name;
    private javax.swing.JTextArea ta_chat;
    // Termino de declaracion de variables //GEN-Fin:variables
}
