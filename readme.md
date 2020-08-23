# Loading.Bar

A custom loading view in which dots turn into bars and back again :D

![one](https://raw.githubusercontent.com/itsaligadimi/LoadingDotBar/master/20200823_122254.jpg)
![two](https://raw.githubusercontent.com/itsaligadimi/LoadingDotBar/master/20200823_121711.jpg)
![three](https://raw.githubusercontent.com/itsaligadimi/LoadingDotBar/master/20200823_122246.jpg)

## Installation

Step 2. Add it in your root build.gradle at the end of repositories:

	allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
Step 2. Add the dependency

	dependencies {
	        implementation 'com.github.itsaligadimi:LoadingDotBar:v1.0'
	}

## Usage

```python
<com.agadimi.loadingdotbar.LoadingDotBarView
    android:id="@+id/loading"
    android:layout_width="wrap_content"
    android:layout_height="wrap_content"
    app:duration="1000"
    app:animation_cycle_delay="1000"
    app:dot_color="#c7cfac"
    app:bar_height="22dp"
    app:dot_radius="4dp"
    app:gap_size="5dp"/>
    
    
LoadingDotBarView loadingDotBarView = findViewById(R.id.loading);
loadingDotBarView.start();
```
width and height of the view are calculated based on the size of dots. So you can keep them 'wrap_content'.

**duration**: (milliseconds) the time it takes to complete a cycle

**animation_cycle_delay**: (milliseconds) the delay between each cycle

**dot_color**: (color) color of the dots

**bar_height**: (dimension) height of the bar at its maxmimum

**dot_radius**: (dimension) dot radius

**gap_size**: (dimension) the gap between each dot

## Contributing
If you wanna change anything, feel free to do it
