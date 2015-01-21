import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayDeque;
import java.util.ArrayList;

public class Main {
  public static class Graph {
    public Noeud[] noeuds;

    public Graph(String path){
      //this.time = System.currentTimeMillis();
      try {
        Scanner sc = new Scanner(new File(path));
        int nb = sc.nextInt();
        noeuds = new Noeud[nb];
        for(int i=1;i<=nb;i++){
          noeuds[i-1]=new Noeud(i);
        }
        while (sc.hasNextInt()) {
          int a = sc.nextInt() - 1;
          int b = sc.nextInt() - 1;
          noeuds[a].fils.add(noeuds[b]);
          noeuds[b].fils.add(noeuds[a]);
        }
      } catch (FileNotFoundException e) {
        System.out.println("No such file or Directory / Or wrong type of file");
        System.exit(1);
      }

      //System.out.println(this.time - System.currentTimeMillis());
    }
    public void nParcours(){
      int diametre = 0;
      int nDia = 0;
      for(Noeud n : this.noeuds){
        nDia = n.parcoursSimple();
        if(diametre<nDia)diametre = nDia;
        //clean();
      }
      System.out.println(diametre);
    }

    public void habibParcours(){
      Noeud last = noeuds[0].parcoursLast();
      Noeud middle = last.parcoursMiddle();
      last = middle.parcoursLast();
      System.out.println(last.parcoursSimple());
    }

    public void clean(){
      for(Noeud n : noeuds){
        n.couleur = true;
        n.distance = 0;
      }
    }
  }

  public static class Noeud {
    public static final ArrayDeque<Noeud> queue = new ArrayDeque<Noeud>();
    public int number;
    public final ArrayList<Noeud> fils = new ArrayList<Noeud>();
    public boolean couleur = true;
    public int distance = 0;
    public final ArrayList<Noeud> chemin = new ArrayList<Noeud>();

    public Noeud(int number){
      this.number = number;
    }

    public int parcoursSimple(){
      this.distance = 0;
      Noeud courant = this;
      this.couleur = !this.couleur;
      queue.add(this);
      while(!queue.isEmpty()){
        courant = queue.pop();
        for(Noeud n : courant.fils){
          if(n.couleur != this.couleur){
            n.couleur = !n.couleur;
            n.distance = courant.distance+1;
            queue.add(n);
          }
        }
      }
      return courant.distance;
    }


    public Noeud parcoursLast(){
      this.distance = 0;
      Noeud first = this;
      this.couleur = !this.couleur;
      queue.add(this);
      Noeud courant = this;
      while(queue.size()>0){
        courant = queue.pop();
        for(Noeud n : courant.fils){
          if(n.couleur != this.couleur){
            n.couleur = !n.couleur;
            n.distance = courant.distance+1;
            queue.add(n);
            //if(n.distance>first.distance)first = n;
            if (n.distance>first.distance)first = n;
            else if(n.distance == first.distance && n.number<first.number)first = n;
          }
        }
      }
      return first;
    }

    public Noeud parcoursMiddle(){
      this.distance = 0;
      Noeud first = this;
      Noeud courant = this;
      this.couleur = !this.couleur;
      queue.add(this);
      while(queue.size()>0){
        courant = queue.pop();
        for(Noeud n : courant.fils){
          if(n.couleur != this.couleur){
            n.couleur = !n.couleur;
            n.distance = courant.distance+1;
            n.chemin.addAll(courant.chemin);
            n.chemin.add(courant);
            queue.add(n);
            if (n.distance>first.distance)first = n;
            else if(n.distance == first.distance && n.number<first.number)first = n;
          }
        }
      }
      return first.chemin.get((first.chemin.size())/2);
    }


  }

  public static void main(String[] args) {
      if(args.length<2) System.out.println("usage : java Main <e/h> fichier.pfg");
      else if(!args[1].endsWith(".pfg")) System.out.println("usage : java Main <e/h> fichier.pfg");
      else if(args[0].equals("e")){
        Graph g = new Graph(args[1]);
        g.nParcours();
      }
      else if(args[0].equals("h")){
        Graph g = new Graph(args[1]);
        g.habibParcours();
      }
      else System.out.println("usage : java Main <e/h> fichier.pfg");
  }
}
