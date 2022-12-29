<template id="app-layout">
  <div class="app-layout">
    <div class="container">
      <!-- Start of navbar -->
      <nav class="navbar navbar-expand-lg navbar-light">
        <button class="navbar-toggler" type="button" data-toggle="collapse"
                data-target="#navbarSupportedContent" aria-controls="navbarSupportedContent"
                aria-expanded="false" aria-label="Toggle navigation">
          <span class="navbar-toggler-icon"></span>
        </button>

        <div class="collapse navbar-collapse" id="navbarSupportedContent">
          <ul class="navbar-nav mr-auto">
            <li class="nav-item">
              <a class="navbar-brand" href="/">Analytics</a>
            </li>
            <li class="nav-item">
              <a class="nav-link" href="/jetfit">Fitness</a>
            </li>
          </ul>
        </div>
      </nav>
      <!-- End of navbar -->
      <div class="content mt-3">
        <div class="container-fluid">
          <slot></slot>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
Vue.component("app-layout", {template: "#app-layout"});
</script>

<style>
.navbar{
  background-color: #E1D7C6;
}
</style>
