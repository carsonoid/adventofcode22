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
