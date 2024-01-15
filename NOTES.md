<!-- Improved compatibility of back to top link: See: https://github.com/othneildrew/Best-README-Template/pull/73 -->
<div id="top"></div>

<!-- PROJECT LOGO -->
<br />
<div align="center">
  <h2 align="center">Joy of Energy</h2>
  <!--div>
    <img src="images/profile_pic.png" alt="Logo" width="80" height="80">
  </div-->
</div>

<!-- TABLE OF CONTENTS -->

<!-- ## Table of Contents -->

<ol>
    <li>
        <a href="#about-the-project">About The Project</a>
        <ul>
            <li><a href="#users">Users</a></li>
            <li><a href="#available-endpoints">Available Endpoints</a></li>
        </ul>
    </li>
    <li>
        <a href="#design">Design</a>
        <ul>
            <li><a href="#add-readings">Add Readings</a></li>
            <li><a href="#get-readings">Get Readings</a></li>
            <li><a href="#compare-price-plan">Compare Price Plan</a></li>
            <li><a href="#recommend-price-plan">Recommend Price Plan</a></li>
        </ul>
    </li>
    <!--li>
        <a href="#implementation">Implementation</a>
        <ul>
            <li><a href="#XXXX-implementation">XXXX</a></li>
        </ul>
    </li>
    <li>
        <a href="#testing">Testing</a>
        <ul>
            <li><a href="#unit-testing">Unit Testing</a></li>
            <li><a href="#integration-testing">Integration Testing</a></li>
        </ul>
    </li>
    <li>
        <a href="#Usage">Usage</a>
        <ul>
            <li><a href="#via-postman">Via Postman</a></li>
        </ul>
    </li>
    <li>
        <a href="#future-considerations">Future Considerations</a>
        <ul>
            <li><a href="#xxxxx">XXXXX</a></li>
        </ul>
    </li-->
    <li><a href="#acknowledgments">Acknowledgments</a></li>
</ol>

<!-- ABOUT THE PROJECT -->

## About The Project

This is an assignment based on the [thoughtworks][tw-project-repo] repo, and the code is forked in my [own][self-project-repo] repo for the assessment.

More information can be found [here][self-project-repo].

<p align="right">(<a href="#top">back to top</a>)</p>

### Users

The following shows the users and their corresponding energy plans:

| ![user-energy-plans][img-users] |
|:--:|
| *User Energy Plan* |

<p align="right">(<a href="#top">back to top</a>)</p>

### Available Endpoints

The following shows the available endpoints for the application:

| ![available-endpoints][img-available-endpoints] |
|:--:|
| *Available Endpoints* |

<p align="right">(<a href="#top">back to top</a>)</p>

## Design

The following are the design for the respective endpoints listed above:
* Add Readings
* Get Readings
* Compare Price Plan
* Recommend Price Plan

<p align="right">(<a href="#top">back to top</a>)</p>

### Add Readings

The following is the design for the `add readings` endpoint:

| ![add-readings][img-sequence-add-readings] |
|:--:|
| *Add Readings Design* |

<p align="right">(<a href="#top">back to top</a>)</p>

### Get Readings

The following is the design for the `get readings` endpoint:

| ![get-readings][img-sequence-get-readings] |
|:--:|
| *Get Readings Design* |

<p align="right">(<a href="#top">back to top</a>)</p>

### Compare Price Plan

The following is the design for the `compare price plan` endpoint:

| ![compare-price-plan][img-sequeunce-compare-plan] |
|:--:|
| *Compare Price Plan Design* |

<p align="right">(<a href="#top">back to top</a>)</p>

### Recommend Price Plan

The following is the design for the `recommend price plan` endpoint:

| ![recoomend-price-plan][img-sequeunce-recommend-plan] |
|:--:|
| *Recommend Price Plan Design* |

<p align="right">(<a href="#top">back to top</a>)</p>

<!-- ACKNOWLEDGMENTS -->

## Acknowledgments

- [Readme Template][template-resource]
- [Thoughtworks Joy Of Energy Repo][tw-project-repo]
- [Own Joy Of Energy Repo][self-project-repo]

<p align="right">(<a href="#top">back to top</a>)</p>

---

<!-- MARKDOWN LINKS & IMAGES -->

[template-resource]: https://github.com/othneildrew/Best-README-Template/blob/master/README.md

[self-project-repo]: https://github.com/D3vYuan/developer-joyofenergy-java
[tw-project-repo]: https://github.com/techops-recsys-lateral-hiring/developer-joyofenergy-java

[self-task]: https://docs.google.com/document/d/1TRL3VtUoCCngkp-LZB8UeOmsxSQM6BgclZ34hXX0F5Y

[ref-fix-gradlew-permission-denied]: https://copyprogramming.com/howto/why-when-i-use-github-actions-ci-for-a-gradle-project-i-face-gradlew-permission-denied-error

[img-users]: ./diagram/use-case.png
[img-available-endpoints]: ./diagram/available-endpoints.png
[img-sequence-add-readings]: ./diagram/sequence-1-add-readings.png
[img-sequence-get-readings]: ./diagram/sequence-2-get-readings.png
[img-sequeunce-compare-plan]: ./diagram/sequence-3-compare-price-plan.png
[img-sequeunce-recommend-plan]: ./diagram/sequence-4-recommend-price-plan.png