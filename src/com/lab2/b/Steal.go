package main

import (
	"fmt"
	"time"
)

type Ivanov struct {}

type Petrov struct {}

type Nechyporuk struct {}

type Item struct { cost int }

type Warehouse struct {
	items []Item
}

func (Ivanov) take(warehouse Warehouse, item chan Item) {
	for i := 0; i < len(warehouse.items); i++ {
		item <- warehouse.items[i]
		fmt.Println("Ivanov is on his way with an item from the warehouse.")
		time.Sleep(5 * time.Microsecond)
	}
}

func (Petrov) load(itemsToTruck chan Item, itemsToCount chan Item) {
	for i := 0; i < cap(itemsToCount); i++ {
		propertyItem := <-itemsToTruck
		fmt.Println("Petrov took an item from Ivanov.")
		itemsToCount <- propertyItem
		fmt.Println("Petrov gave an item to Nechyporuk.")
		time.Sleep(5 * time.Microsecond)
	}
}

func (Nechyporuk) count(itemsToCount chan Item, sum chan int) {
	var total = 0
	for i := 0; i < cap(itemsToCount); i++ {
		propertyItem := <-itemsToCount
		fmt.Println("Nechyporuk took an item from Petrov")
		total += propertyItem.cost
		time.Sleep(5 * time.Microsecond)
	}
	sum <- total
}

func main() {
	var warehouse = Warehouse{[]Item{ {4}, {5}, {7},
											{1}, {4}, {1},
											{8}, {3}, {2}}}

	var output = make(chan int)
	var itemsToTruck = make(chan Item, len(warehouse.items))
	var itemsToCount = make(chan Item, cap(itemsToTruck))

	ivanov := Ivanov{}
	petrov := Petrov{}
	nechyporuk := Nechyporuk{}

	go ivanov.take(warehouse, itemsToTruck)
	go petrov.load(itemsToTruck, itemsToCount)
	go nechyporuk.count(itemsToCount, output)

	fmt.Println("Total is: ", <- output)
}