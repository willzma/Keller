![alt text](http://i.imgur.com/Eo3RJaC.png)


http://www.oldkeller.com

##Inspiration
The inspiration for Keller came to us as we began to take notice of all the things sighted people take for granted: the ability to read dollar notes, bathroom signs, room numbers, and so much more. We wanted to build an application that extended far beyond any accessibility app we have ever undertaken. Keller is just that.

With Keller, we’re making learning Braille an immersive and intuitive experience for both the sighted and visually impaired. Typically, learning Braille can take months to years. Keller’s unique built-in Braille Book and Braille Learning Modules captured through OCR technology and harness them into an engaging learning experience.

The inspiration for our product’s name comes from the most prominent blind women in history, Helen Keller. Much like how Helen Keller’s miracle that story begins with her experiencing water and communicating with Anne Sullivan, we seek to build a platform that promotes learning language through immersion and engagement. Our logo represents the water pump so symbolic to the story of Helen Keller.

##What it does
Keller is an Android app with a wide range of purposes, all aimed at helping both blind and sighted people learn Braille. One of the major features of Keller is the built in OCR camera, which enables students to digitally capture and have read aloud texts and signage they may come across during their everyday life. For the blind and visually impaired, this is a huge accessibility tool. But Keller takes it a step further: users have the option to convert that captured text to Braille and store in a personal database called the Braille Book. You can also make entries in your Braille Book with the simple speech-to-text service built in. Here, students can access stored Braille phrases and haptically engage with them through our state of the art Braille Learning Modules. Learning modules enable students to actually feel the Braille they have stored through the use of vibration. This helps build memorization and boosts engagement. Keller has an ingenious UX features, with every UI element having an auditory component, enabling the blind to navigate the user interface with ease.

##How we built it
Keller is an Android app, built using the OCR.space API along with several of Google’s text to speech libraries in order to deliver the best possible learning experience for users. Data is all stored in FireBase, and requests to the APIs were completed using Volley. The Braille Learning Modules were created using Android’s built in buttons and cursor sensing, for proximity detection.

##Challenges we ran into
The first challenge we really had was finding an optical character recognition API to convert our images to text that we could work with. After that, development stalled frequently around development of our Braille learning modules as they are contained in the BrailleBook, which had to be coded mostly from scratch. We argued about how best to create a sort of index-card, quick and easy learning method for blind people. What that reduced to was coming up with a way to simulate Braille bumps on a screen, which we implemented with vibration around the user’s fingers; we converted text into Braille symbols; displayed those Braille symbols with finger-manipulable Android UI elements, and then provided auditory confirmation of a successfully mimed symbol. All these things coming together really brings back an old ghost; the taming of the thread, as well as a new challenge in making extensive use of matrix and boolean operations. Then we had to take all that and incorporate it with online databases with Firebase and Linode, which only added to the more than ample complexity of Keller.

##Accomplishments that we're proud of
While our team has some experience with multithreaded Android applications and asynchronous functions through a variety of APIs and in-house solutions, what we tackled with this app was on a much higher level of complexity compared to anything we’ve done before. The number of threads ramped up, and the amount of work and code we could delegate to APIs decreased dramatically. What resulted was a lot of clever work to create an intuitive user interface for blind people, text to speech to read back what you’ve just learned, and speech to text so you can even read it back and confirm it for certain. Keller really ends up becoming, then, almost like a Braille teacher all on its own.

##What we learned
We really learned a great deal about the less common sides of Android development. File manipulation, camera usage, and asynchronous task management, were among these aspects. We also needed to learn a lot about databases and HTTP GET and POST calls to properly and effectively interact with data.

##What’s next for Keller
One of our initial goals was to create a way for Keller to be used over Amazon Echo. Echo has a great deal of artificial intelligence that would assist with the accurate study of Braille. A way that we would leverage this AI would be to quiz users on Braille that they have already learned through the Learning Modules, or that they have intentionally added to the Braille Book themselves. This would also increase usability for the visually impaired because of Amazon Echo’s auditory interface.
