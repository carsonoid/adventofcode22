public class App {
    public static void main(String[] args) throws Exception {
        var jetMaker = new JetMaker(args[0]);
        var rockMaker = new RockMaker(System.getProperty("user.dir") + "/input/rocks.txt");

        // for (var i = 0; i < 5; i++) {
        // Chamber chamber = new Chamber();
        // var rock = rockMaker.Make();
        // rock.SetTo(2, 0, chamber);
        // rock.RightEdge();
        // chamber.AddRock(rock);
        // chamber.Print();
        // }

        Chamber chamber = new Chamber();

        // var rock = rockMaker.Make();
        // rock.SetTo(2, 0, chamber);
        // chamber.AddRock(rock);

        // rock = rockMaker.Make();
        // rock.SetTo(2, 2, chamber);
        // rock.RightEdge();
        // chamber.AddRock(rock);

        // rock = rockMaker.Make();
        // rock.SetTo(2, 4, chamber);
        // rock.RightEdge();
        // chamber.AddRock(rock);

        var rocksFallen = 0;
        Rock rock = null;
        var loops = 0;
        while (rocksFallen < 2022) {
            System.out.println("Loop: " + loops);

            if (rock == null) {
                rock = rockMaker.Make();
                rock.SetTo(2, chamber.Start());
            }

            // if (loops == 13) {
            // chamber.AddRock(rock);
            // chamber.Print();
            // System.exit(0);
            // }

            rock.Move(jetMaker.Make(), chamber);

            var moved = rock.Move('v', chamber);
            if (!moved) {
                chamber.AddRock(rock);
                // chamber.Print();
                rock = null;
                rocksFallen++;
            }

            loops++;
        }

        System.out.println(chamber.Height());
    }
}
