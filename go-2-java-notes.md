# Notes From A Go Dev Writing Java

# Map literals are ugly

None of the options feel half as good as go literals

```java
Map<String, String> data = Stream.of(new String[][] {
  { "Hello", "World" }, 
  { "John", "Doe" }, 
}).collect(Collectors.toMap(data -> data[0], data -> data[1]));
```

Just doesn't feel as good as this:

```go
var data = map[string]string{
    "Hello": "World",
    "John": "Doe",
}
```

Other options:  https://www.baeldung.com/java-initialize-hashmap

# I miss variadic fmt.Println()

I would rather use commas to separate arguments then to concat things.
- This might just be me not intuitively think that I can just add strings and ints together

This is idiomatic Go

```go
fmt.Println("totalScore:", 10)
```

Java has a different way to do this:

```java
// this doesn't compile:
System.out.println("totalScore:", 10);

// and this just feels ~wrong~ but does work
System.out.println("totalScore: " + 10);

// This feels about the same as in Go
System.out.printf("totalScore: %d\n", 10);
```

# Why is slicing strings so hard?

Go, Python, Ruby, Rust, Javascript... this is easy in basically every other language.

But there i no handy `list[start:end]` syntax or related function (as far as I can tell) in Java

EDIT:

I guess subList does work, but getting between arrays and lists to be able to sublist is kind of hard.

TLDR: Day 3 input parsing took way longer than I would have expected;

```Java
inputList.subList(0, inputList.size() / 2),
```

# Day 6: How well do `List` implementations handle ops?

I did this by adding and removing items from the front of lists. At this scale it didn't matter
and I could have written it to add and remove off the end.

But this does bring up a good point. Is it as intesive to mess with the start and end of Lists in Java
as it is in Go?  `List` has a lot of methods that really don't seem to push you away from it.

Compare that to Go where the only "easy" thing to do with slices is `append`. All other ops are more
work and syntax-heavy (see the "slice tricks" doc) which sort of push you away from them naturally.


# I wish class did name then type not type then name

```java
String Name;
Boolean IsDir;
Integer Size;

public void Print(String s) { // ...
```

Is less intuitive to me than

```go
Name string
IsDir string
Size int

func Print(s string) { // ...
```

# Without first class functions implementing a Dir walk sucked and felt gross

I ended up passing a map down the line, I would have much preferred using a callback and
a closure over the map.

But that may just be how I'm used to doing things.


# I hate that you can use `==` to compare strings even though you shouldn't

The compiler is more than happy to let you compare strings with `==` even though it won't work in many/most cases.
