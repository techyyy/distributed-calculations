package main

import (
	"fmt"
	"math/rand"
	"sync"
	"time"
)

const (
	CustomersCount = 10
	GoCount        = 3
)

func getARandomCustomer() string {
	var customers = []string{"John Doe", "Anne Thorn", "Abigail Mac", "Michael Colton", "Jim Barnes",
					"Ellen Woods", "Anthony Mayer", "Cole Watson", "Mickey McJohnson", "John Xina"}
	return customers[rand.Intn(len(customers))]
}

func haircut(queue <- chan string) {
	served := 0
	for {
		customer := <-queue
		fmt.Printf("%s has got a haircut.", customer)
		fmt.Println()
		time.Sleep(5 * time.Second)
		served++
		if served == CustomersCount { return }
	}
}

func haircutting(input <- chan string, queue chan <- string) {
	for {
		name, open := <-input
		if open {
			time.Sleep(5 * time.Second)
			fmt.Printf("%s comes to salon.", name)
			fmt.Println()
			queue <- name
		} else {
			return
		}
	}
}

func main() {
	customers, queue := make(chan string, CustomersCount), make(chan string, CustomersCount)
	var waitGroup sync.WaitGroup
	waitGroup.Add(GoCount + 1)

	go func() {
		haircut(queue)
		waitGroup.Done()
	}()

	for i := 0; i < GoCount; i++ {
		go func() {
			haircutting(customers, queue)
			waitGroup.Done()
		}()
	}

	for i := 0; i < CustomersCount; i++ { customers <- getARandomCustomer() }
	close(customers)

	waitGroup.Wait()
}