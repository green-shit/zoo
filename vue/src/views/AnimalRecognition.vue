<template>
    <div>
        <input type="file" @change="onFileChange">
        <button @click="recognizeAnimal">Recognize</button>
        <div v-if="animalRecognized">{{ animalRecognized }}</div>
    </div>
</template>

<script>
export default {
    name: "AnimalRecognition",
    data() {
        return {
            selectedFile: null,
            animalRecognized: null
        };
    },
    methods: {
        onFileChange(event) {
            this.selectedFile = event.target.files[0];
        },
        recognizeAnimal() {
            let formData = new FormData();
            formData.append("file", this.selectedFile);

            fetch("http://localhost:8080/recognizeAnimal", {
                method: "POST",
                body: formData
            })
                .then(response => response.text())
                .then(data => {
                    this.animalRecognized = data;
                })
                .catch(error => console.error("Error:", error));
        }
    }
}

</script>

<style scoped>

</style>