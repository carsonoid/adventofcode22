package main

import (
	"fmt"
	"strconv"
)

func main() {
	doThings()
}

func goPrint() {
	// pretty standard
	fmt.Println("totalScore:", 10)

	// prints round numbers starting at 1
	for i := 0; i < 10; i++ {
		fmt.Println("round:", i+1)
	}
}

func goLiterals() {
	users := []string{"user1", "user2"}

	data := map[string]string{
		"Hello": "World",
		"John":  "Doe",
	}

	groups := map[string][]string{
		"group1": {"user1", "user2"},
		"group2": {"user3", "user4"},
	}

	fmt.Println(users, data, groups)
}

func goLists() {
	fmt.Println("Hello world"[:5])

	users := []string{"user1", "user2", "user3"}
	fmt.Println(users[:1])
}

func listOps() {
	var users []string

	// standard append is easy and clean
	// and usuaually cheap-ish
	users = append(users, "user2")

	// inserting at 0 is a bit more expensive
	// and requires a more verbose statement
	// > maybe by design?
	users = append([]string{"user1"}, users...)
}

// START VARS OMIT

var (
	Name  string
	IsDir string
	Size  int
)

func println(s string) {
	// ...
	// END VARS OMIT
	fmt.Println(s)
}

// START FCF OMIT
func walkGrid(grid [][]string, f func(x, y int, val string)) {
	for y, row := range grid {
		for x, val := range row {
			f(x, y, val)
		}
	}
}

func printGrid() {
	grid := [][]string{
		{"a", "b", "c"},
		{"d", "e", "f"},
		{"g", "h", "i"},
	}

	walkGrid(grid, func(x, y int, val string) {
		fmt.Printf("%d,%d: %s", x, y, val)
	})
}

// END FCF OMIT

func compareExamples() {
	x := "foo"
	y := "foo"

	if x == "foo" && y == "foo" {
		fmt.Println("this works")
	}

	if x == y {
		fmt.Println("this also works")
	}

	// pointer comparisons can still be done but are more obvious
	// when they are happening
	if &x == &y {
		fmt.Println("this does not work")
	}
}

func switchExample(s string) {
	switch s {
	case "1":
		fmt.Println("1")
	case "2":
		fmt.Println("2")
	case "fallthrough":
		fmt.Println("fallthrough")
		fallthrough
	default:
		fmt.Println("default")
	}
}

// START OVERLOAD OMIT
func doThings() {
	doThingString("1")
	doThing(1)
}

func doThingString(s string) {
	x, err := strconv.Atoi(s)
	if err != nil {
		panic(err)
	}

	doThing(x)
}

func doThing(i int) {
	fmt.Println("doThing with", i)
}

// END OVERLOAD OMIT
