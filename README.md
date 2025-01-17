# Sorting_Algorithm_Visualization  <img src="https://raw.githubusercontent.com/devicons/devicon/master/icons/java/java-original.svg" alt="java logo" width="60" height="60"/>

This project provides a comprehensive visualization of four fundamental sorting algorithms: Insertion Sort, Merge Sort, Heap Sort, and Quick Sort. The visualization is implemented using Java, making it an educational tool for understanding how these algorithms work.
- **Insertion Sort Visualization:** Demonstrates the process of building a sorted array one element at a time.
- **Quick Sort Visualization:** Shows the divide-and-conquer approach to sorting by partitioning arrays.
- **Heap Sort Visualization:** Illustrates the process of converting an array into a heap and sorting it.
- **Merge Sort Visualization:** Displays the process of dividing arrays into smaller sub-arrays and merging them back in sorted order.

## Team Members
1. [Aishwarya J S](https://github.com/Aish-h)
2. [Akhil Mohan](https://github.com/Akhil-Mohan-github)
3. [Aleena Bino](https://github.com/aleena24bino)
4. [Aliya Nawal](https://github.com/Aliyanawal)
5. [Amal Babu](https://github.com/amalb03)

## Project Snapshots

### Heap Sort
![heap](https://github.com/aleena24bino/Sorting_Algorithm_Visualization/assets/148476197/e04cd702-b5a2-4623-9d4d-246a162c9a6d)

### Quick Sort
![quick](https://github.com/aleena24bino/Sorting_Algorithm_Visualization/assets/148476197/9eeb7720-33dd-4632-beec-452762ee415b)

### Merge Sort
![merge](https://github.com/aleena24bino/Sorting_Algorithm_Visualization/assets/148476197/dd70f361-0938-4e09-a3b6-cb9d170504d5)

### Insertion Sort
![insert](https://github.com/aleena24bino/Sorting_Algorithm_Visualization/assets/148476197/7b649c0f-3c0b-4863-aa5d-21e6aa315baa)


## How it works?

**1. Overall Structure:**
* The main class Sort6 and its associated helper classes (Arr, Arr_Box, GUI_Display_Arr) form the core of the application.
* Sorting algorithms such as Insertion Sort, Merge Sort, Quick Sort, and Heap Sort are implemented and visualized through this GUI.

**2.Visualization Setup:**
* Java's Swing framework is used for GUI components (JFrame, JPanel, JButton, etc.).
* Components are organized into panels (input, update_btn, shuffle_btn, output1, output2, etc.) to manage user input, algorithm execution, and displaying results.

**3.Algorithm Implementation:**
* Each sorting algorithm (insertion(), merge(), quick(), heap()) is encapsulated within methods that perform the sorting logic and update the GUI accordingly.
* For instance, Heap Sort (heap()) initializes a max heap, performs heapification, and repeatedly extracts the maximum element to achieve sorting.

**4.GUI Interaction:**
* The GUI_Display_Arr class manages the visualization of arrays and tree structures representing sorting algorithms.
* Methods like addArr(), addTree(), warpup(), etc., handle updating the GUI with arrays, tree structures, and text descriptions of algorithm steps.

**5.User Controls:**
* Buttons (start, stop, previous, next) control the flow of visualization, allowing users to start, stop, and navigate through algorithm steps.
* Input fields (input) accept user-defined arrays for sorting, which are then visualized and processed by the sorting algorithms.

**6.Execution Flow:**
* When the program starts, users input an array, choose a sorting algorithm, and initiate sorting.
* The GUI updates dynamically to show the current state of the array and algorithm execution step-by-step.


## Project Video

https://github.com/aleena24bino/Sorting_Algorithm_Visualization/assets/118409571/c3b87f5d-6110-4f40-98a3-9e3ee09fb6c0

## How to configure?

1. Install Java Development Kit (JDK)
2. Install Visual Studio Code
3. Set Environmental Variable for Java in the system.
             <div>(i)Set the Variable name as 'JAVA_HOME'</div>
             <div>(ii)Set the Variable value to the path of your JDK installation directory.</div> 
             <div>(iii)Under System Variables ->Path ->Edit ->New -> Add '%JAVA_HOME%\bin'</div>
4. Verify Installation
   
    In Command Prompt(cmd)
    ```
    echo %JAVA_HOME%
    ```
     Then
     ```
     java --version
    ```
5. Install VS Code Extensions

    Search for "Java Extension Pack" and install it.
   
7. Clone this repository

## How to run?

     
   ```
    javac Sort6.java
   ```
     
  ```
    java Sort6
  ```       
 
