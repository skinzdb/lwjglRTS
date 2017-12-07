//package renderEngine;
//
//import java.io.BufferedReader;
//import java.io.FileNotFoundException;
//import java.io.FileReader;
//import java.io.IOException;
//import java.util.logging.Level;
//import java.util.logging.Logger;
//
//import org.joml.Vector3f;
//
//public class ModelLoader {
//
//    public ModelLoader loadModel(String fileName){
//        try {
//            BufferedReader r = new BufferedReader(new FileReader(fileName));
//            //System.out.println(r.readLine());
//            String nl = r.readLine();
//            int vertCount = 0;
//            int normCount = 0;
//            int texcoCount = 0;
//            int indCount = 0;
//            float[] tempVert1 = new float[0];
//            float[] tempVert2;
//            float[] tempNormals1 = new float[0];
//            float[] tempNormals2;
//            float[] tempTexco1 = new float[0];
//            float[] tempTexco2;
//            int[] tempInds1 = new int[0];
//            int[] tempInds2;
//            
//            while(nl!=null){
//                String[] parts = nl.split(" ");
//                if("v".equals(parts[0])){
//                    vertCount+=3;
//                    tempVert2 = tempVert1;
//                    tempVert1 = new float[vertCount];
//                    for(int c = 0; c < tempVert2.length; c++){
//                        tempVert1[c] = tempVert2[c];
//                    }
//                    tempVert1[vertCount-1] = Float.valueOf(parts[1]);
//                    tempVert1[vertCount-2] = -Float.valueOf(parts[2]);
//                    tempVert1[vertCount-3] = Float.valueOf(parts[3]);
//                    
//                }else if("vn".equals(parts[0])){
//                    normCount+=3;
//                    tempNormals2 = tempNormals1;
//                    tempNormals1 = new float[normCount];
//                    for(int c = 0; c < tempNormals2.length; c++){
//                        tempNormals1[c] = tempNormals2[c];
//                    }
//                    tempNormals1[normCount-3] = Float.valueOf(parts[1]);
//                    tempNormals1[normCount-2] = Float.valueOf(parts[2]);
//                    tempNormals1[normCount-1] = Float.valueOf(parts[3]);
//                    
//                }else if("vt".equals(parts[0])){
//                    texcoCount+=2;
//                    tempTexco2 = tempTexco1;
//                    tempTexco1 = new float[texcoCount];
//                    for(int c = 0; c < tempTexco2.length; c++){
//                        tempTexco1[c] = tempTexco2[c];
//                    }
//                    tempTexco1[texcoCount-2] = Float.valueOf(parts[1]);
//                    tempTexco1[texcoCount-1] = Float.valueOf(parts[2]);
//                    
//                }else if("f".equals(parts[0])){
//                    
//                    indCount+=9;
//                    tempInds2 = tempInds1;
//                    tempInds1 = new int[indCount];
//                    for(int c = 0; c < tempInds2.length; c++){
//                        tempInds1[c] = tempInds2[c];
//                    }
//                    for(int i = 1; i < parts.length; i++){
//                        String[] vertexPeices = parts[i].split("/");
//                        tempInds1[indCount-(3*i)] = Integer.valueOf(vertexPeices[0]);
//                        tempInds1[indCount-(3*i)+1] = Integer.valueOf(vertexPeices[1]);
//                        tempInds1[indCount-(3*i)+2] = Integer.valueOf(vertexPeices[2]);
//                    }
//                    
//                }else{
//                    System.out.println(nl);
//                }
//                
//            
//                nl = r.readLine();
//            }
//            System.out.println(tempVert1.length+" "+tempTexco1.length+" "+tempNormals1.length+" "+tempInds1.length);
//            int[] indices = new int[tempInds1.length/3];
//            for(int i = 0; i < indices.length; i++){
//                indices[i] = i;
//            }
//            float[] vertices = new float[indices.length*3];
//            for(int i = 0; i < vertices.length; i+=3){
//            	vertices[3*i] = tempVert1[tempInds1[3*i]*3];
//            	vertices[3*i+1] = tempVert1[tempInds1[3*i]*3+1];
//            	vertices[3*i+2] = tempVert1[tempInds1[3*i]*3+2];
//            }
//            float[] texCos = new float[indices.length*2];
//            for(int i = 0; i < texCos.length; i+=2){
//            	texCos[2*i] = tempTexco1[tempInds1[2*i+1]*2];
//            	texCos[2*i+1] = tempTexco1[tempInds1[2*i+1]*2+1];
//            }
//            float[] normals = new float[indices.length*3];
//            for(int i = 0; i < normals.length; i+=3){
//            	normals[3*i] = tempNormals1[tempInds1[3*i+2]*3];
//            	normals[3*i+1] = tempNormals1[tempInds1[3*i+2]*3+1];
//            	normals[3*i+2] = tempNormals1[tempInds1[3*i+2]*3+2];
//            }
//            
//            System.out.println("limb vertices loaded");
//            return null;
//            
//        } catch (FileNotFoundException ex) {
//            Logger.getLogger(ModelLoader.class.getName()).log(Level.SEVERE, null, ex);
//        } catch (IOException ex) {
//            Logger.getLogger(ModelLoader.class.getName()).log(Level.SEVERE, null, ex);
//        }
//    }
//    
//}
