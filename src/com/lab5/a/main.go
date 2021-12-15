package main

import (
	"fmt"
	"math/rand"
	"sync"
	"time"
)

const (
	CrewSize = 200
	RowSize  = 50
	Right    = true
	Left     = false
)

func generateCrew() [CrewSize]bool {
	var crew [CrewSize]bool
	for i := 0; i < CrewSize; i++ {
		if rand.Intn(2) == 0 {
			crew[i] = Left
		} else {
			crew[i] = Right
		}
	}
	return crew
}

func commandCrew(crew *[]bool, waitGroup *sync.WaitGroup) {
	defer waitGroup.Done()
	var (
		i       = 0
		size    = len(*crew)
		changed = true
	)
	for changed {
		changed = false
		i = 0
		for ; i < size-1; i++ {
			if (*crew)[i] != (*crew)[i+1] {
				(*crew)[i] = !(*crew)[i]
				changed = true
			}
		}
	}
}

func imitateCommand(crew [CrewSize]bool) [CrewSize]bool {

	var waitGroup sync.WaitGroup

	for i := 0; i < CrewSize/RowSize; i++ {
		waitGroup.Add(1)
		crewSlice := crew[i*RowSize : (i+1)*RowSize]
		go commandCrew(&crewSlice, &waitGroup)
	}
	waitGroup.Wait()
	return crew
}

func printCrew(crew [CrewSize]bool) {
	for i := 0; i < CrewSize/RowSize; i++ {
		fmt.Println(crew[i*RowSize : (i+1)*RowSize])
	}
}

func main() {
	rand.Seed(time.Now().UnixNano())
	crew := generateCrew()
	fmt.Println("Before command:")
	printCrew(crew)
	crew = imitateCommand(crew)
	fmt.Println("After command:")
	printCrew(crew)
}
